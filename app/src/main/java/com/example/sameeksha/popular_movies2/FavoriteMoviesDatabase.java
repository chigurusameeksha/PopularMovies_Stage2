package com.example.sameeksha.popular_movies2;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {FavoriteMovies.class}, version = 1)
public abstract class FavoriteMoviesDatabase extends RoomDatabase {

    public  abstract FavoriteMoviesDao databaseFavoriteMovieDao();

    private static FavoriteMoviesDatabase INSTANCE;

    public static FavoriteMoviesDatabase getDatabase(final Context context){
        if(INSTANCE ==null){
            synchronized (FavoriteMoviesDatabase.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(), FavoriteMoviesDatabase.class,"favoriteMovies_database" )
                            .build();
                }
            }
        }
        return  INSTANCE;
    }

}
