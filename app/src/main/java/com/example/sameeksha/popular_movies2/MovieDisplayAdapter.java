package com.example.sameeksha.popular_movies2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieDisplayAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Movie> movies;

    MovieDisplayAdapter(Context context,ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }


    @Override
    public int getCount() {

        Log.i("count",String.valueOf(movies.size()));
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        ImageView imageView;

        ViewHolder(View view) {
            imageView = view.findViewById(R.id.imageView);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {




        ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(context);

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.single_row, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(context)
                .load(movies.get(position).getMovieImagePath())
                .placeholder(R.drawable.progress_file)
                .fit()
                .error(R.drawable.ic_launcher_background)
                .centerCrop()
                .into(holder.imageView); // View where image is loaded.


        return convertView;
    }


}
