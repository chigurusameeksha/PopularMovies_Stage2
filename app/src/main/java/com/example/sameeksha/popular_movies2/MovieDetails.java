package com.example.sameeksha.popular_movies2;


import android.content.Context;
import android.content.SharedPreferences;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MovieDetails extends AppCompatActivity {
    final String ratingDenominator = "/10";
    ArrayList<MovieReviews> movieReviewsArrayList = new ArrayList<>();
    ArrayList<MovieTrailer> movieTrailerArrayList = new ArrayList<>();
    Movie movie;
    FavoriteMovies favoriteMovies1;
    MovieTrailerRecyclerViewAdapter movieTrailerRecyclerViewAdapter;
    RecyclerView trailerRecyclerview;
    LinearLayoutManager trailerLinearLayoutManager = new LinearLayoutManager(MovieDetails.this);


    MovieReviewRecyclerViewAdapter movieReviewRecyclerViewAdapter;
    RecyclerView reviewRecyclerView;
    LinearLayoutManager reviewLinearLayoutManager = new LinearLayoutManager(MovieDetails.this);

    //private static final String SHARED_PF_NAME = "movieSP";
    private static final String SHARED_PF_NAME = "favoriteMovies";
    private static final String CHECK_BOX_STATE = "check_state";
    private SharedPreferences sharedPreferences;
    CheckBox checkBox;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_details);
        getSupportActionBar().setTitle("Movie Details");


        if (getIntent().getExtras().get("isFromMovies").equals(true)) {
            //Toast.makeText(this,"match found" ,Toast.LENGTH_SHORT ).show();

            // get movie id of the movie that was just passed
            movie = getIntent().getParcelableExtra(MainActivity.MOVIE_IN_CURRENT_CLICKED_POSITION);
            final int movieIdOfMovieInCurrentlyClicked = movie.getMovieid();

            final String key = Integer.toString(movie.getMovieid());

            // final FavoriteMovies favoriteMovies = new FavoriteMovies(movie.getMovieid(),movie.getMovieRating(),movie.getMovieTitle(),movie.getMovieImagePath(),movie.getMovieDescripton(),movie.getMovieReleaseDate());

            //get path for trailer and reviews
            String movieVideoPath = "https://api.themoviedb.org/3/movie/" + movieIdOfMovieInCurrentlyClicked + "/videos?api_key=" + MovieDataSource.API_KEY;
            String movieReviewPath = "https://api.themoviedb.org/3/movie/" + movieIdOfMovieInCurrentlyClicked + "/reviews?api_key=" + MovieDataSource.API_KEY;
            // initiate asynctask
            new MovieTrailerAsyncTask().execute(movieVideoPath);
            new MovieReviewAsyncTask().execute(movieReviewPath);

            // trailer recycler
            trailerRecyclerview = findViewById(R.id.trailers_RecyclerView);
            trailerRecyclerview.setHasFixedSize(true);
            trailerRecyclerview.setLayoutManager(trailerLinearLayoutManager);
            movieTrailerRecyclerViewAdapter = new MovieTrailerRecyclerViewAdapter(MovieDetails.this, movieTrailerArrayList);
            trailerRecyclerview.setAdapter(movieTrailerRecyclerViewAdapter);


            //reviews recycler
            reviewRecyclerView = findViewById(R.id.reviews_recyclerview);
            trailerRecyclerview.setHasFixedSize(true);
            reviewRecyclerView.setLayoutManager(reviewLinearLayoutManager);
            movieReviewRecyclerViewAdapter = new MovieReviewRecyclerViewAdapter(MovieDetails.this, movieReviewsArrayList);
            reviewRecyclerView.setAdapter(movieReviewRecyclerViewAdapter);


            TextView movieTitleTextView = findViewById(R.id.movieTitle);
            ImageView movieImageView = findViewById(R.id.movieImage);
            TextView movieReleaseDateView = findViewById(R.id.movieReleaseDate);
            TextView movieRatingView = findViewById(R.id.movieRating);
            TextView movieDescriptionView = findViewById(R.id.movieDescription);


            Picasso.with(this)
                    .load(movie.getMovieImagePath())
                    .fit()
                    .placeholder(R.drawable.progress_file)
                    .error(R.drawable.ic_launcher_background)
                    .into(movieImageView);

            movieTitleTextView.setText(movie.getMovieTitle());
            movieReleaseDateView.setText(movie.getMovieReleaseDate());
            movieRatingView.setText(String.format("%s%s", String.valueOf(movie.getMovieRating()), ratingDenominator));
            movieDescriptionView.setText(movie.getMovieDescripton());


            sharedPreferences = getSharedPreferences(SHARED_PF_NAME, Context.MODE_PRIVATE);
            checkBox = findViewById(R.id.checkbox_button);
            // does the key of this movie exists inside the SP? If yes,
            // get the bolean state saved with this key when it was
            // checked in the listener and set Checkbox as true to show the yellow star as marked/already marked as favorite
            // If no such key exists inside SP, set checkbox as false(showing grey star)
            checkBox.setChecked(sharedPreferences.getBoolean(key, false));
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //sharedPreferences.edit().putBoolean(CHECK_BOX_STATE, isChecked).apply();

                    if (isChecked) {
                        sharedPreferences.edit().putBoolean(key, true).apply();// save movie's state state to SP if marked as favorite
                        FavoriteMovies favoriteMovies = new FavoriteMovies(movie.getMovieid(), movie.getMovieRating(), movie.getMovieTitle(), movie.getMovieImagePath(), movie.getMovieDescripton(), movie.getMovieReleaseDate());


                        Toast.makeText(getApplicationContext(), "added to favorites",
                                Toast.LENGTH_SHORT).show();
                        MainActivity.favoriteMoviesViewModel.insertMoviesIntoDatabaseVmodel(favoriteMovies);


                    } else {
                        //sharedPreferences.edit().putBoolean(key, false).apply();
                        sharedPreferences.edit().remove(key).apply();// remove from SP if unchecked
                        final FavoriteMovies favoriteMovies = new FavoriteMovies(movie.getMovieid(), movie.getMovieRating(), movie.getMovieTitle(), movie.getMovieImagePath(), movie.getMovieDescripton(), movie.getMovieReleaseDate());

                        Toast.makeText(getApplicationContext(), "removed from favorites",
                                Toast.LENGTH_SHORT).show();
                        MainActivity.favoriteMoviesViewModel.unFavoriteAMovie(favoriteMovies);
                    }


                }
            });


        } else {


            favoriteMovies1 = getIntent().getParcelableExtra(MainActivity.MOVIE_IN_CURRENT_CLICKED_POSITION);
            final int movieIdOfMovieInCurrentlyClicked = favoriteMovies1.getId();

            // get movie id of the movie that was just passed

            final String key = Integer.toString(favoriteMovies1.getId());

            // final FavoriteMovies favoriteMovies = new FavoriteMovies(movie.getMovieid(),movie.getMovieRating(),movie.getMovieTitle(),movie.getMovieImagePath(),movie.getMovieDescripton(),movie.getMovieReleaseDate());

            //get path for trailer and reviews
            String movieVideoPath = "https://api.themoviedb.org/3/movie/" + movieIdOfMovieInCurrentlyClicked + "/videos?api_key=" + MovieDataSource.API_KEY;
            String movieReviewPath = "https://api.themoviedb.org/3/movie/" + movieIdOfMovieInCurrentlyClicked + "/reviews?api_key=" + MovieDataSource.API_KEY;
            // initiate asynctask
            new MovieTrailerAsyncTask().execute(movieVideoPath);
            new MovieReviewAsyncTask().execute(movieReviewPath);

            // trailer recycler
            trailerRecyclerview = findViewById(R.id.trailers_RecyclerView);
            trailerRecyclerview.setHasFixedSize(true);
            trailerRecyclerview.setLayoutManager(trailerLinearLayoutManager);
            movieTrailerRecyclerViewAdapter = new MovieTrailerRecyclerViewAdapter(MovieDetails.this, movieTrailerArrayList);
            trailerRecyclerview.setAdapter(movieTrailerRecyclerViewAdapter);


            //reviews recycler
            reviewRecyclerView = findViewById(R.id.reviews_recyclerview);
            trailerRecyclerview.setHasFixedSize(true);
            reviewRecyclerView.setLayoutManager(reviewLinearLayoutManager);
            movieReviewRecyclerViewAdapter = new MovieReviewRecyclerViewAdapter(MovieDetails.this, movieReviewsArrayList);
            reviewRecyclerView.setAdapter(movieReviewRecyclerViewAdapter);


            TextView movieTitleTextView = findViewById(R.id.movieTitle);
            ImageView movieImageView = findViewById(R.id.movieImage);
            TextView movieReleaseDateView = findViewById(R.id.movieReleaseDate);
            TextView movieRatingView = findViewById(R.id.movieRating);
            TextView movieDescriptionView = findViewById(R.id.movieDescription);


            Picasso.with(this)
                    .load(favoriteMovies1.getMovieImagePath())
                    .fit()
                    .placeholder(R.drawable.progress_file)
                    .error(R.drawable.ic_launcher_background)
                    .into(movieImageView);

            movieTitleTextView.setText(favoriteMovies1.getMovieTitle());
            movieReleaseDateView.setText(favoriteMovies1.getMovieReleaseDate());
            movieRatingView.setText(String.format("%s%s", String.valueOf(favoriteMovies1.getMovieRating()), ratingDenominator));
            movieDescriptionView.setText(favoriteMovies1.getMovieDescripton());


            sharedPreferences = getSharedPreferences(SHARED_PF_NAME, Context.MODE_PRIVATE);
            checkBox = findViewById(R.id.checkbox_button);
            // does the key of this movie exists inside the SP? If yes,
            // get the bolean state saved with this key when it was
            // checked in the listener and set Checkbox as true to show the yellow star as marked/already marked as favorite
            // If no such key exists inside SP, set checkbox as false(showing grey star)
            checkBox.setChecked(sharedPreferences.getBoolean(key, false));
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //sharedPreferences.edit().putBoolean(CHECK_BOX_STATE, isChecked).apply();

                    if (isChecked) {
                        sharedPreferences.edit().putBoolean(key, true).apply();// save movie's state state to SP if marked as favorite
                        FavoriteMovies favoriteMovies = new FavoriteMovies(favoriteMovies1.getId(), favoriteMovies1.getMovieRating(), favoriteMovies1.getMovieTitle(), favoriteMovies1.getMovieImagePath(), favoriteMovies1.getMovieDescripton(), favoriteMovies1.getMovieReleaseDate());


                       Toast.makeText(getApplicationContext(), "added to favorites",
                                Toast.LENGTH_SHORT).show();
                        MainActivity.favoriteMoviesViewModel.insertMoviesIntoDatabaseVmodel(favoriteMovies);


                    } else {
                        //sharedPreferences.edit().putBoolean(key, false).apply();
                        sharedPreferences.edit().remove(key).apply();// remove from SP if unchecked
                        FavoriteMovies favoriteMovies = new FavoriteMovies(favoriteMovies1.getId(), favoriteMovies1.getMovieRating(), favoriteMovies1.getMovieTitle(), favoriteMovies1.getMovieImagePath(), favoriteMovies1.getMovieDescripton(), favoriteMovies1.getMovieReleaseDate());

                        Toast.makeText(getApplicationContext(), "removed from favorites",
                                Toast.LENGTH_SHORT).show();
                        MainActivity.favoriteMoviesViewModel.unFavoriteAMovie(favoriteMovies);
                    }


                }
            });

        }




    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if ( id == android.R.id.home ) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class MovieTrailerAsyncTask extends AsyncTask<String, Void, Integer> {


        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;

            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;
            InputStream inputStream;

            try {
                URL url = new URL(params[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                int status = httpURLConnection.getResponseCode();

                if (status != HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getErrorStream();
                    result = 0;
                } else {
                    inputStream = httpURLConnection.getInputStream();
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    parseMovieTrailerResponse(stringBuilder.toString());
                    result = 1;

                }
               /* if (stringBuilder.length() == 0) {
                    return null;
                }*/
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return result;

        }

        @Override
        protected void onPostExecute(Integer result) {

            if (result == 1) {
                movieTrailerRecyclerViewAdapter = new MovieTrailerRecyclerViewAdapter(MovieDetails.this, movieTrailerArrayList);
                trailerRecyclerview.setAdapter(movieTrailerRecyclerViewAdapter);

            } else {
                Toast.makeText(MovieDetails.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }


        }


    }


    //*************************************************

    public class MovieReviewAsyncTask extends AsyncTask<String, Void, Integer> {


        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;

            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;
            InputStream inputStream;

            try {
                URL url = new URL(params[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                int status = httpURLConnection.getResponseCode();

                if (status != HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getErrorStream();
                    result = 0;
                } else {
                    inputStream = httpURLConnection.getInputStream();
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    parseMovieReviewsResponse(stringBuilder.toString());
                    result = 1;

                }
               /* if (stringBuilder.length() == 0) {
                    return null;
                }*/
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return result;

        }

        @Override
        protected void onPostExecute(Integer result) {

            if (result == 1) {
                movieReviewRecyclerViewAdapter = new MovieReviewRecyclerViewAdapter(MovieDetails.this, movieReviewsArrayList);
                reviewRecyclerView.setAdapter(movieReviewRecyclerViewAdapter);

            } else {
                Toast.makeText(MovieDetails.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }

        }


    }

    // Methods
    //*************************************************
    private void parseMovieTrailerResponse(String result) {

        String trailer = "";
        String trailerType = "";
        MovieTrailer movieTrailer;


        try {
            JSONObject entireJson = new JSONObject(result);
            JSONArray resultJsonArray = entireJson.getJSONArray("results");
            JsonObject object;
            if (resultJsonArray.length() > 0) {
                for (int i = 0; i < resultJsonArray.length(); i++) {


                    trailer = resultJsonArray.getJSONObject(i).optString("key");
                    trailerType = resultJsonArray.getJSONObject(i).optString("type");
                    movieTrailer = new MovieTrailer(trailer, trailerType);
                    movieTrailerArrayList.add(movieTrailer);
                }

            }
            if (movieTrailerArrayList.size() > 0) {
                for (int i = 0; i < movieTrailerArrayList.size(); i++) {
                    Log.v("THE VIDEO KEYS ARE:", movieTrailerArrayList.get(i).getTrailer());
                }
            } else {
                Log.v("Sorry", "No video links for this movie");
            }

            Log.v("Videolist onpostsize", String.valueOf(movieTrailerArrayList.size()));
        } catch (JSONException e) {

            e.printStackTrace();
        }


    }

    //*************************************************
    // Json parser for reviews
    private void parseMovieReviewsResponse(String result) {
        String reviewAuthor;
        String reviewContent;
        MovieReviews movieReviews;

        Log.v("THIS IS REVIEW JSON", result);
        try {
            JSONObject entireJson = new JSONObject(result);
            JSONArray resultJsonArray = entireJson.getJSONArray("results");
            movieReviewsArrayList = new ArrayList<>();


            if (resultJsonArray.length() > 0) { // check if there are reviews
                for (int i = 0; i < resultJsonArray.length(); i++) {

                    reviewAuthor = resultJsonArray.getJSONObject(i).optString("author");
                    reviewContent = (resultJsonArray.getJSONObject(i).optString("content"));
                    movieReviews = new MovieReviews(reviewAuthor, reviewContent);
                    movieReviewsArrayList.add(movieReviews);

                }

            }
            if (movieReviewsArrayList.size() > 0) {// check if there are reviews in the list
                for (int i = 0; i < movieReviewsArrayList.size(); i++) {
                    Log.v("THE VIDEO KEYS ARE:", movieReviewsArrayList.get(i).getContents());
                }

            } else {
                Log.v("Sorry", "No reviews for this movie");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
//*************************************************


}