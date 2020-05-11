package com.example.sameeksha.popular_movies2;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity(tableName = "favorite_movies")
public class FavoriteMovies implements Parcelable {
    @PrimaryKey
    @NonNull
    private int id;

    private double movieRating;

    private String movieTitle;

    private String movieImagePath;

    private String movieDescripton;

    private String movieReleaseDate;

    @Ignore
    final String path1 = "http://image.tmdb.org/t/p";
    @Ignore
    final String path2 = "w185";

    public FavoriteMovies(@NonNull int id, double movieRating, String movieTitle, String movieImagePath, String movieDescripton, String movieReleaseDate) {
        this.id = id;
        this.movieRating = movieRating;
        this.movieTitle = movieTitle;
        this.movieImagePath = movieImagePath;
        this.movieDescripton = movieDescripton;
        this.movieReleaseDate = movieReleaseDate;
    }

    protected FavoriteMovies(Parcel in) {
        id = in.readInt();
        movieRating = in.readDouble();
        movieTitle = in.readString();
        movieImagePath = in.readString();
        movieDescripton = in.readString();
        movieReleaseDate = in.readString();
    }

    public static final Creator<FavoriteMovies> CREATOR = new Creator<FavoriteMovies>() {
        @Override
        public FavoriteMovies createFromParcel(Parcel in) {
            return new FavoriteMovies(in);
        }

        @Override
        public FavoriteMovies[] newArray(int size) {
            return new FavoriteMovies[size];
        }
    };

    public double getMovieRating() {
        return movieRating;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMovieImagePath() {
        // return "https://image.tmdb.org/t/p/w185" + movieImagePath; // movie path;
        return movieImagePath;
    }

    public String getMovieDescripton() {
        return movieDescripton;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeDouble(movieRating);
        dest.writeString(movieTitle);
        dest.writeString(movieImagePath);
        dest.writeString(movieDescripton);
        dest.writeString(movieReleaseDate);
    }
}
