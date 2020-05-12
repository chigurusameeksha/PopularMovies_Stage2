package com.example.sameeksha.popular_movies2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_POPULAR = 1;
    public static final int REQUEST_CODE_TOP_RATED = 2;
    public String topRatedMovieUrl = "https://api.themoviedb.org/3/movie/top_rated?api_key=";
    public String popularMoviesUrl = "https://api.themoviedb.org/3/movie/popular?api_key=";
    public final static String MOVIE_IN_CURRENT_CLICKED_POSITION = "theMovieInCurrentClickedPosition";
    private int counter = 0;
    private int movieIdOfMovieInCurrentlyClicked;
    private ArrayList<Movie> popularMoviesList = new ArrayList<>();
    private ArrayList<Movie> topRatedMoviesList = new ArrayList<>();
    private ArrayList<Movie> movies = new ArrayList<>();
    private int moviePosition;
    public static FavoriteMoviesViewModel favoriteMoviesViewModel;
    private static Movie movieIncurrentClickedPosition;
    FavoriteMovieAdapter favoriteMovieAdapter = new FavoriteMovieAdapter(this);
    GridView gridView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = findViewById(R.id.gridview);
        MovieDataSource object = new MovieDataSource();


        // I got help with the below method from stackoverflow a question i asked
        //https://stackoverflow.com/questions/50553815/using-volley-to-make-2-separate-remote-requests-with-one-method
        OnResponseListener<ArrayList<Movie>> listener = new OnResponseListener<ArrayList<Movie>>() {

            @Override
            public void onSuccess(int tag, ArrayList<Movie> object) {
                if (tag == REQUEST_CODE_POPULAR) {
                    popularMoviesList = object; // initialize popularmovieslist
                    movies.addAll(object); //Add movies to movies
                    Log.i("number of movies added:", String.valueOf(popularMoviesList.size()));
                    counter++; // tracking control/number of times onSuccess in called

                } else if (tag == REQUEST_CODE_TOP_RATED) {
                    topRatedMoviesList = object;
                    movies.addAll(object);
                    Log.i("number of movied added:", String.valueOf(topRatedMoviesList.size()));
                    counter++; // tracking Onsuccess call by Listener
                }

                //Collections.shuffle(movies); // popular + top rated movies----shuffle them. This is default display to the user when they first launch the app
                Log.i("counter=", String.valueOf(counter));
                if (counter == 2) { // Do not call the adapter and pass movies for display until background process finishes and movie contains both pop and toprated
                    Log.i("num of movies passed:", String.valueOf(movies.size()));
                    gridView.setAdapter(new MovieDisplayAdapter(MainActivity.this, movies));
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            movieIdOfMovieInCurrentlyClicked = movies.get(position).getMovieid();
                            moviePosition = position;
                            Intent intent = new Intent(MainActivity.this, MovieDetails.class);
                            //When clicked, the position of the current movie
                            Movie movieIncurrentClickedPosition = movies.get(moviePosition);
                            intent.putExtra(MOVIE_IN_CURRENT_CLICKED_POSITION, movieIncurrentClickedPosition);
                            intent.putExtra("isFromMovies", true);
                            startActivity(intent);

                        }
                    });
                }

            }

            @Override
            public void onError(Exception e) {
//            todo handle error
            }


        };
        object.movieArrayList(popularMoviesUrl, REQUEST_CODE_POPULAR, listener);
        object.movieArrayList(topRatedMovieUrl, REQUEST_CODE_TOP_RATED, listener);

        // ViewModel
        favoriteMoviesViewModel = ViewModelProviders.of(this).get(FavoriteMoviesViewModel.class);

        favoriteMoviesViewModel.getAllFavoriteMovieInViewModelFromRepo().observe(this, new Observer<List<FavoriteMovies>>() {
            @Override
            public void onChanged(@Nullable List<FavoriteMovies> favoriteMovies) {
                //favoriteMoviesArrayList= (ArrayList<FavoriteMovies>) favoriteMovies;
                favoriteMovieAdapter.setWords((ArrayList<FavoriteMovies>) favoriteMovies);
            }
        });


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();               // Add this line
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.popular) {
            GridView gridView = findViewById(R.id.gridview);
            getSupportActionBar().setTitle("Most Popular Movies");
            gridView.setAdapter(new MovieDisplayAdapter(this, popularMoviesList));
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(MainActivity.this, "" + "popularity, number:" + position, LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, MovieDetails.class);
                    Movie movieIncurrentClickedPosition = popularMoviesList.get(position);
                    intent.putExtra(MOVIE_IN_CURRENT_CLICKED_POSITION, movieIncurrentClickedPosition);
                    intent.putExtra("isFromMovies", true);
                    startActivity(intent);

                }
            });

        } else if (item.getItemId() == R.id.toprated) {
            GridView gridView = findViewById(R.id.gridview);
            getSupportActionBar().setTitle("Top Rated Movies");
            gridView.setAdapter(new MovieDisplayAdapter(this, topRatedMoviesList));
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    //Toast.makeText(MainActivity.this, "" + "Rated number:" + position, LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, MovieDetails.class);
                    movieIncurrentClickedPosition = topRatedMoviesList.get(position);
                    intent.putExtra(MOVIE_IN_CURRENT_CLICKED_POSITION, movieIncurrentClickedPosition);
                    intent.putExtra("isFromMovies", true);
                    startActivity(intent);


                }
            });

        } else if (item.getItemId() == R.id.favorites) {
            GridView gridView = findViewById(R.id.gridview);
            getSupportActionBar().setTitle("My Favorite Movies");
            if (favoriteMovieAdapter.getCount() < 1) {
                Toast.makeText(this, "No movies added yet", LENGTH_SHORT).show();
            }
            gridView.setAdapter(favoriteMovieAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MainActivity.this, MovieDetails.class);
                    FavoriteMovies currentFavoriteMovieClicked = (FavoriteMovies) favoriteMovieAdapter.getItem(position);
                    intent.putExtra(MOVIE_IN_CURRENT_CLICKED_POSITION, currentFavoriteMovieClicked);
                    intent.putExtra("isFromMovies", false);
                    startActivity(intent);


                }
            });



        } else {
            return true;

        }
        return super.onOptionsItemSelected(item);


    }



}



