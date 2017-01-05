package com.example.weichenglau.personalDiary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class StoryContentActivity extends AppCompatActivity {
    private TextView date, title, content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment2_layout);

        date = (TextView)findViewById(R.id.date);
        title = (TextView)findViewById(R.id.title);
        content = (TextView)findViewById(R.id.content);

        String getdate = getIntent().getStringExtra("day") + "-" + getIntent().getStringExtra("month") + "-" + getIntent().getStringExtra("year");
        date.setText(getdate);
        title.setText(getIntent().getStringExtra("title"));
        content.setText(getIntent().getStringExtra("content"));

    }
}
