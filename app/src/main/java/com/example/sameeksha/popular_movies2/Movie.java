package com.example.sameeksha.popular_movies2;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private int movieid;

    private double movieRating;

    private String movieTitle;

    private String movieImagePath;

    private String movieDescripton;

    private String movieReleaseDate;
    final String path1 = "http://image.tmdb.org/t/p";
    final  String path2 = "w185";

    public Movie(int movieid, double movieRating,String movieTitle, String movieImagePath, String movieDescripton, String movieReleaseDate){
        this.movieid = movieid;
        this.movieRating = movieRating;
        this.movieTitle=movieTitle;
        this.movieImagePath=movieImagePath;
        this.movieDescripton=movieDescripton;
        this.movieReleaseDate=movieReleaseDate;


    }

    public void setMovieid(int movieid) {
        this.movieid = movieid;
    }
    public void setMovieRating(double movieRating){
        this.movieRating=movieRating;
    }
    public void setMovieTitle(String movieTitle){
        this.movieTitle =movieTitle;
    }

    public void setMovieImagePath(String movieImagePath) {
        this.movieImagePath = movieImagePath;
    }

    public void setMovieDescripton(String movieDescripton) {
        this.movieDescripton = movieDescripton;
    }

    public void setMovieReleaseDate(String movieReleaseDate) {
        this.movieReleaseDate = movieReleaseDate;
    }

    public int getMovieid() {
        return movieid;
    }
    public double getMovieRating(){
        return movieRating ;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMovieImagePath() {
        return "https://image.tmdb.org/t/p/w185" + movieImagePath; // movie path
    }

    public String getMovieDescripton() {
        return  movieDescripton;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.movieid);
        dest.writeDouble(this.movieRating);
        dest.writeString(this.movieTitle);
        dest.writeString(this.movieImagePath);
        dest.writeString(this.movieDescripton);
        dest.writeString(this.movieReleaseDate);

    }

    private Movie(Parcel in) {
        this.movieid = in.readInt();
        this.movieRating = in.readDouble();
        this.movieTitle = in.readString();
        this.movieImagePath = in.readString();
        this.movieDescripton = in.readString();
        this.movieReleaseDate = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
