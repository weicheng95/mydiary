package com.example.weichenglau.personalDiary.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.weichenglau.personalDiary.HomeActivity;
import com.example.weichenglau.personalDiary.Newpost;
import com.example.weichenglau.personalDiary.R;
import com.example.weichenglau.personalDiary.adapters.MyAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by weichenglau on 01/01/2017.
 */

public class StoryFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private Context context;
    private String[] title = new String[]{};
    private String[] content;
    private RecyclerView.Adapter<MyAdapter.ViewHolder> mAdapter;
    private DatabaseReference root;
    private FirebaseAuth firebaseAuth;
    private TextView tv;
    private int counter = 0;
    private ArrayList<Newpost> posts=new ArrayList<Newpost>();
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        view = inflater.inflate(R.layout.activity_main_recycleview, container, false);
        getActivity().setTitle("Home");
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser()!=null){
            root =  FirebaseDatabase.getInstance().getReference();
            String getuser = firebaseAuth.getCurrentUser().getUid();
            root =  FirebaseDatabase.getInstance().getReference().child(getuser);
        }


        mRecyclerView = (RecyclerView)view.findViewById(R.id.my_recycler_view);
        // 若設為FixedSize可以增加效率不過就喪失了彈性
        mRecyclerView.setHasFixedSize(true);

        // 選擇一種Layout管理器這邊是選擇（linear layout manager）
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyAdapter(context, posts);
        mRecyclerView.setAdapter(mAdapter);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    posts.clear();
                for (DataSnapshot postsnapshot: dataSnapshot.getChildren()){
                    Newpost post = postsnapshot.getValue(Newpost.class);
                    posts.add(post);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
