package com.example.sameeksha.popular_movies2;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FavoriteMoviesDao {
    @Insert
    void insertOneMovieIntoDatabase(FavoriteMovies favoriteMovie);

    @Delete
    void deleteOneMovieFromDatabase(FavoriteMovies favoriteMovie);

    @Query("SELECT * from favorite_movies")
    LiveData<List<FavoriteMovies>> getAllFavoriteMovies();


}

