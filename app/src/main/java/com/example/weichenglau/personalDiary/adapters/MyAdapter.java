package com.example.weichenglau.personalDiary.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weichenglau.personalDiary.HomeActivity;
import com.example.weichenglau.personalDiary.Newpost;
import com.example.weichenglau.personalDiary.R;
import com.example.weichenglau.personalDiary.StoryContentActivity;
import com.example.weichenglau.personalDiary.StoryContentActivity1;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<Newpost> posts=new ArrayList<Newpost>();
    private static Context context;

    //get data from MainActivity
    public MyAdapter(Context context, ArrayList<Newpost> posts) {
        MyAdapter.context = context;
        this.posts = posts;
        //Collections.reverse(th
        // is.posts);

    }


	public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView daytv;
        TextView monthtv;
        TextView yeartv;
        TextView titletv;
        TextView contenttv;
        Context context;
        ArrayList<Newpost> post=new ArrayList<Newpost>();

        public ViewHolder(View itemView, Context context, ArrayList<Newpost> posts) {
            super(itemView);
            daytv = (TextView) itemView.findViewById(R.id.daytextView);
            monthtv = (TextView) itemView.findViewById(R.id.monthtextView);
            yeartv = (TextView) itemView.findViewById(R.id.yeartextView);
            titletv = (TextView) itemView.findViewById(R.id.titletv);
            contenttv = (TextView) itemView.findViewById(R.id.contenttv);
            this.context = context;
            this.post = posts;
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Newpost newpost = post.get(position);
            Intent intent = new Intent(this.context, StoryContentActivity1.class);
            intent.putExtra("year",newpost.year);
            intent.putExtra("month",newpost.month);
            intent.putExtra("day",newpost.day);
            intent.putExtra("title",newpost.title);
            intent.putExtra("content",newpost.content);
            this.context.startActivity(intent);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_text_view, viewGroup, false);
        // ViewHolder參數一定要是項目的根目錄節點。
        return new ViewHolder(view, context, posts);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.daytv.setText(posts.get(i).day);
        viewHolder.monthtv.setText(posts.get(i).month);
        viewHolder.yeartv.setText(posts.get(i).year);
        viewHolder.titletv.setText(posts.get(i).title);
        viewHolder.contenttv.setText(posts.get(i).content);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


}
