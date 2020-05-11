package com.example.sameeksha.popular_movies2;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class FavoriteMoviesRepository {

    private FavoriteMoviesDao repositoryFavoriteMoviesDao;
    private LiveData<List<FavoriteMovies>> allfavoriteMoviesInRepoFromDatabase;

    FavoriteMoviesRepository(Application application) {
        FavoriteMoviesDatabase database = FavoriteMoviesDatabase.getDatabase(application);
        repositoryFavoriteMoviesDao = database.databaseFavoriteMovieDao();
        allfavoriteMoviesInRepoFromDatabase = repositoryFavoriteMoviesDao.getAllFavoriteMovies();

    }


    LiveData<List<FavoriteMovies>> getAllfavoriteMovieInTheDatabase() {
        return allfavoriteMoviesInRepoFromDatabase;
    }

    public void insertFavoriteMovieIntoDatabase(FavoriteMovies favoriteMovies) {
        new insertMoviesIntoDatabaseAsynTask(repositoryFavoriteMoviesDao).execute(favoriteMovies);

    }


    //insert movies using background thread
    private static class insertMoviesIntoDatabaseAsynTask extends AsyncTask<FavoriteMovies, Void, Void> {

        private FavoriteMoviesDao insertAsyncTaskfavoriteMoviesDao;


        insertMoviesIntoDatabaseAsynTask(FavoriteMoviesDao favoriteMoviesDao) {
            insertAsyncTaskfavoriteMoviesDao = favoriteMoviesDao;
        }

        @Override
        protected Void doInBackground(FavoriteMovies... favoriteMovies) {
            insertAsyncTaskfavoriteMoviesDao.insertOneMovieIntoDatabase(favoriteMovies[0]);

            return null;
        }
    }

    public void deleteAMovieInTheDatabase(FavoriteMovies favoriteMovies) {
        new deleteMovieAsyncTask(repositoryFavoriteMoviesDao).execute(favoriteMovies);
    }

    private static class deleteMovieAsyncTask extends AsyncTask<FavoriteMovies, Void, Void> {
        private FavoriteMoviesDao deleteAsyncTaskfavoriteMoviesDao;

        public deleteMovieAsyncTask(FavoriteMoviesDao favoriteMoviesDao) {
            this.deleteAsyncTaskfavoriteMoviesDao = favoriteMoviesDao;
        }

        @Override
        protected Void doInBackground(FavoriteMovies... favoriteMovies) {
            deleteAsyncTaskfavoriteMoviesDao.deleteOneMovieFromDatabase(favoriteMovies[0]);

            return null;
        }
    }

}
