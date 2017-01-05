package com.example.weichenglau.personalDiary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weichenglau.personalDiary.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddPostActivity extends AppCompatActivity {
    private TextView daytv;
    private TextView monthtv;
    private TextView yeartv;


    private EditText addpostTitle;
    private EditText addpostContent;
    private DatabaseReference root;
    private FirebaseAuth firebaseAuth;

    private int year;
    private int month;
    private int day;
    private String monthInString;;
    private String dayInString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firebaseAuth = FirebaseAuth.getInstance();
        this.setTitle("Add Diary");
        if (firebaseAuth.getCurrentUser()!=null){
            root =  FirebaseDatabase.getInstance().getReference();
            String getuser = firebaseAuth.getCurrentUser().getUid().toString();
            root =  FirebaseDatabase.getInstance().getReference().child(getuser);
        }


        daytv = (TextView)findViewById(R.id.day);
        monthtv= (TextView)findViewById(R.id.month);
        yeartv = (TextView)findViewById(R.id.year);
        addpostTitle = (EditText)findViewById(R.id.addpostTitle);
        addpostContent = (EditText)findViewById(R.id.addpostContent);

        Calendar c = Calendar.getInstance();
        //SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        //String formattedDate = df.format(c.getTime());
        //addpostDate.setText(formattedDate);

        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        switch (month){
            case 0:
                monthInString = "JAN";
                break;
            case 1:
                monthInString = "FEB";
                break;
            case 2:
                monthInString = "MAR";
                break;
            case 3:
                monthInString = "APRIL";
                break;
            case 4:
                monthInString = "MAY";
                break;
            case 5:
                monthInString = "JUN";
                break;
            case 6:
                monthInString = "JULY";
                break;
            case 7:
                monthInString = "AUG";
                break;
            case 8:
                monthInString = "SEP";
                break;
            case 9:
                monthInString = "OCT";
                break;
            case 10:
                monthInString = "NOV";
                break;
            case 11:
                monthInString = "DEC";
                break;
        }
        int day = c.get(Calendar.DAY_OF_MONTH);

        if(day<10){
            dayInString = "0" + day;
        }
        yeartv.setText(""+year);
        monthtv.setText(""+monthInString);
        daytv.setText(""+dayInString);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addpost_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.save) {

            String addposttitle = addpostTitle.getText().toString();
            String addpostcontent = addpostContent.getText().toString();
            //String date = addpostDate.getText().toString();
            String year = yeartv.getText().toString();
            String month = monthtv.getText().toString();
            String day = daytv.getText().toString();
            writeNewPost(year,month,day, addposttitle, addpostcontent);
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void writeNewPost(String year, String month, String day, String title, String content){

        String key = root.push().getKey();
        Newpost post = new Newpost(year,month,day, title, content);
        Map<String, Object> postmap = post.toMap();
        Map<String, Object> updatemap = new HashMap<String, Object>();

        updatemap.put(key, postmap);
        root.updateChildren(updatemap);

    }
}
