package com.example.weichenglau.personalDiary;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {
    private TextView setting;
    private Context context;
    private PendingIntent sender;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        context = this;
        this.setTitle("Setting");
        setting = (TextView)findViewById(R.id.settingTextView);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 指定鬧鐘設定時間到時要執行AlarmService.class
                Intent intent = new Intent(context, AlarmService.class);

                // 建立PendingIntent
                sender = PendingIntent.getService(context, 100, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                calendar = Calendar.getInstance();
                // 取得按下按鈕時的時間做為TimePickerDialog的預設值
                calendar.setTimeInMillis(System.currentTimeMillis());
                int hour   = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                // 跳出TimePickerDialog來設定時間 */
                TimePickerDialog timePickerDialog = new TimePickerDialog(context, R.style.Theme_AppCompat_Dialog_Alert
                        ,new SettingsActivity.MyOnTimeSetListener(), hour, minute, true);

                timePickerDialog.show();
            }
        });
    }


    // 設定on time監聽器
    private class MyOnTimeSetListener implements
            TimePickerDialog.OnTimeSetListener {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // 取得設定後的時間，秒跟毫秒設為 0
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
			/*
			 * AlarmManager.RTC_WAKEUP設定服務在系統休眠時同樣會執行
			 * 以set()設定的PendingIntent只會執行一次
			 */
            AlarmManager alarmManager =
                    (AlarmManager) getSystemService(ALARM_SERVICE);
            //alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, sender);
            String tmpS = format(hourOfDay) + "：" + format(minute);
            // 以Toast提示設定已完成
            Toast.makeText(context, "設定鬧鐘時間為" + tmpS,
                    Toast.LENGTH_SHORT).show();
        }
    }

    // 日期時間顯示兩位數的method
    private String format(int x) {
        String s = String.valueOf(x);
        return (s.length() == 1)?"0" + s:s;
    }
}
