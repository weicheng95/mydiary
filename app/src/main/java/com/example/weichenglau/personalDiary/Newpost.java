package com.example.weichenglau.personalDiary;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by weichenglau on 02/01/2017.
 */

public class Newpost implements Parcelable {

        public String year;
        public String month;
        public String day;
        public String title;
        public String content;

        public Newpost(){

        }

        public Newpost(String year, String month, String day, String title, String content){
            this.year = year;
            this.month = month;
            this.day = day;
            this.title = title;
            this.content = content;

        }

    protected Newpost(Parcel in) {
        year = in.readString();
        month = in.readString();
        day = in.readString();
        title = in.readString();
        content = in.readString();
    }

    public static final Creator<Newpost> CREATOR = new Creator<Newpost>() {
        @Override
        public Newpost createFromParcel(Parcel in) {
            return new Newpost(in);
        }

        @Override
        public Newpost[] newArray(int size) {
            return new Newpost[size];
        }
    };

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("year", year);
        result.put("month", month);
        result.put("day", day);
        result.put("title", title);
        result.put("content", content);

        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(year);
        dest.writeString(month);
        dest.writeString(day);
        dest.writeString(title);
        dest.writeString(content);
    }
}
