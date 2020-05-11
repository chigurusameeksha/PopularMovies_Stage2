package com.example.sameeksha.popular_movies2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class FavoriteMoviesViewModel extends AndroidViewModel {

    private FavoriteMoviesRepository favoriteMoviesRepository;
    private LiveData<List<FavoriteMovies>> allFavoriteMovieInViewModelFromRepo;

    public FavoriteMoviesViewModel(@NonNull Application application) {
        super(application);
        favoriteMoviesRepository = new FavoriteMoviesRepository(application);
        allFavoriteMovieInViewModelFromRepo=favoriteMoviesRepository.getAllfavoriteMovieInTheDatabase();

    }

    LiveData<List<FavoriteMovies>> getAllFavoriteMovieInViewModelFromRepo(){
        return allFavoriteMovieInViewModelFromRepo;
    }
    public void insertMoviesIntoDatabaseVmodel(FavoriteMovies favoriteMovies){
        favoriteMoviesRepository.insertFavoriteMovieIntoDatabase(favoriteMovies);
    }

    public void unFavoriteAMovie(FavoriteMovies favoriteMovies){
        favoriteMoviesRepository.deleteAMovieInTheDatabase(favoriteMovies);
    }


}
