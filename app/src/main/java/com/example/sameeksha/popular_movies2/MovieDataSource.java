package com.example.sameeksha.popular_movies2;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieDataSource {
    private int count = 1;
    private final static String MOVIE_ID = "id";
    private final static String MOVIE_RATING = "vote_average";
    private final static String MOVIE_TITLE = "original_title";
    private final static String MOVIE_POSTERPATH = "poster_path";
    private final static String MOVIE_DESCRIPTION = "overview";
    private final static String MOVIE_RELEASEDATE = "release_date";
    public static final String API_KEY = BuildConfig.API_KEY;

    private RequestQueue RequestQueue = VolleySingleton.getInstance().getRequestQueue();

    public void movieArrayList(String URL, final int tag, final OnResponseListener<ArrayList<Movie>> listener) {
        final ArrayList<Movie> listOfMovies = new ArrayList<>();
        String url = URL + API_KEY;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        int movieId;
                        double movieRating;
                        String movieTitle, imagePath, movieDescription, movieReleaseDate;
                        try {
                            JSONArray results = response.getJSONArray("results"); // get json array from the object
                            JSONObject object; // to get objects from result array
                            Movie movie;
                            for (int i = 0; i < results.length(); i++) {
                                object = results.getJSONObject(i);// get objects in the array based on their position/index in the array
                                //get values from the objects that will be used to populate instantiated movies
                                movieId = object.getInt(MOVIE_ID);
                                movieRating = object.getDouble(MOVIE_RATING);
                                movieTitle = object.optString(MOVIE_TITLE);
                                imagePath = object.optString(MOVIE_POSTERPATH);
                                movieDescription = object.optString(MOVIE_DESCRIPTION);
                                movieReleaseDate = object.optString(MOVIE_RELEASEDATE);
                                //// for every element in the array( index i), create a new movie
                                movie = new Movie(movieId, movieRating, movieTitle, imagePath, movieDescription, movieReleaseDate);
                                // add each movie created to the arrayList
                                listOfMovies.add(movie);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.i("data call listener :", response.toString());

                        Log.i("called", String.valueOf(count++));
                        listener.onSuccess(tag, listOfMovies);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error);
                        error.printStackTrace();
                        Log.i("error", "onErrorResponse: something went wrong now ");
                    }
                });

        RequestQueue.add(jsonObjectRequest);


    }


}





