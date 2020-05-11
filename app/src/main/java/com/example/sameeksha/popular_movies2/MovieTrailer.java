package com.example.sameeksha.popular_movies2;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieTrailer implements Parcelable {
    private String trailer;
    private  String trailerType;

    public MovieTrailer(String trailer, String trailerTitle) {
        this.trailer = trailer;
        this.trailerType = trailerTitle;
    }

    protected MovieTrailer(Parcel in) {
        trailer = in.readString();
        trailerType = in.readString();
    }

    public static final Creator<MovieTrailer> CREATOR = new Creator<MovieTrailer>() {
        @Override
        public MovieTrailer createFromParcel(Parcel in) {
            return new MovieTrailer(in);
        }

        @Override
        public MovieTrailer[] newArray(int size) {
            return new MovieTrailer[size];
        }
    };

    public String getTrailer() {
        return "https://www.youtube.com/watch?v="+trailer;

    }

    public String getTrailerTitle() {
        return trailerType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(trailer);
        dest.writeString(trailerType);
    }
}
