package com.example.weichenglau.personalDiary;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.example.weichenglau.personalDiary.adapters.MyAdapter;
import com.example.weichenglau.personalDiary.fragment.LoginFragment;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {

    private ArrayList<Newpost> posts=new ArrayList<Newpost>();
    private TextView textView;
    private Context context;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter<MyAdapter.ViewHolder> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recycleview);
        textView = (TextView)findViewById(R.id.textView2);
        posts = getIntent().getParcelableArrayListExtra("post");

        context = this;

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // 若設為FixedSize可以增加效率不過就喪失了彈性
        mRecyclerView.setHasFixedSize(true);

        // 選擇一種Layout管理器這邊是選擇（linear layout manager）
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyAdapter(context, posts);
        mRecyclerView.setAdapter(mAdapter);


    }

    @Override
    public void onBackPressed() {
        finish();


    }
}
