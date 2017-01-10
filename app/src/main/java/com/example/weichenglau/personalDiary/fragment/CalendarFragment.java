package com.example.weichenglau.personalDiary.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weichenglau.personalDiary.Newpost;
import com.example.weichenglau.personalDiary.R;
import com.example.weichenglau.personalDiary.SearchResultActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by weichenglau on 05/01/2017.
 */

public class CalendarFragment extends Fragment {
    private CalendarView calendarView;
    private DatabaseReference root;
    private FirebaseAuth firebaseAuth;
    private String years, months, days, monthInString;
    private TextView textView;
    private ArrayList<Newpost> posts=new ArrayList<Newpost>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_fragment, container, false);
        getActivity().setTitle("calendar");
        calendarView = (CalendarView)view.findViewById(R.id.calendarView);
        firebaseAuth = FirebaseAuth.getInstance();
        textView = (TextView)view.findViewById(R.id.textview1);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (firebaseAuth.getCurrentUser()!=null){
            String getuser = firebaseAuth.getCurrentUser().getUid();
            root =  FirebaseDatabase.getInstance().getReference().child(getuser);
        }

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, final int month, final int dayOfMonth) {
                years = ""+year;
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
                if(dayOfMonth<10){
                    days = "0"+dayOfMonth;
                }else{
                    days = ""+dayOfMonth;
                }
                Toast.makeText(getActivity(),years+monthInString+days,Toast.LENGTH_SHORT).show();
                root.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        posts.clear();
                        for (DataSnapshot postsnapshot: dataSnapshot.getChildren()){
                            Newpost post = postsnapshot.getValue(Newpost.class);
                            if((post.year.equals(years)) && (post.day.equals(days)) && (post.month.equals(monthInString))){
                                //Toast.makeText(getActivity(),years,Toast.LENGTH_SHORT).show();
                                posts.add(post);
                            }
                        }
                        Intent intent = new Intent(getActivity().getApplication(),SearchResultActivity.class);
                        intent.putExtra("post", posts);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

        });
    }
}
