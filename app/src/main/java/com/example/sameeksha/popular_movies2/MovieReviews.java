package com.example.sameeksha.popular_movies2;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieReviews implements Parcelable{

    private  String author;
    private String contents;



    public MovieReviews(String author, String contents) {

        this.author = author;
        this.contents = contents;
    }

    protected MovieReviews(Parcel in) {
        author = in.readString();
        contents = in.readString();
    }

    public static final Creator<MovieReviews> CREATOR = new Creator<MovieReviews>() {
        @Override
        public MovieReviews createFromParcel(Parcel in) {
            return new MovieReviews(in);
        }

        @Override
        public MovieReviews[] newArray(int size) {
            return new MovieReviews[size];
        }
    };

    public String getAuthor() {
        return author;
    }

    public String getContents() {
        return contents;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(contents);
    }
}
