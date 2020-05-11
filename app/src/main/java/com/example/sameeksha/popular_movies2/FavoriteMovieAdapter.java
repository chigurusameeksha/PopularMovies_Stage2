package com.example.sameeksha.popular_movies2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavoriteMovieAdapter extends BaseAdapter {
    private Context context;
    private static ArrayList<FavoriteMovies> favoriteMovies;

    public FavoriteMovieAdapter(Context context) {
        this.context = context;
        //this.favoriteMovies = favoriteMovies;
    }

    void setWords(ArrayList<FavoriteMovies> movies) {
        favoriteMovies = movies;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (favoriteMovies != null) {
            return favoriteMovies.size();
        }
        Toast.makeText(context, "no movies added yet", Toast.LENGTH_SHORT).show();
        return 0;

    }

    @Override
    public Object getItem(int position) {
        return favoriteMovies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    class ViewHolder2 {
        ImageView imageView;

        ViewHolder2(View view) {
            imageView = view.findViewById(R.id.imageView);
        }

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder2 viewHolder2;
        LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.single_row, parent, false);
            viewHolder2 = new ViewHolder2(convertView);
            convertView.setTag(viewHolder2);
        } else {
            viewHolder2 = (ViewHolder2) convertView.getTag();
        }
        Picasso.with(context)
                .load(favoriteMovies.get(position).getMovieImagePath())
                .placeholder(R.drawable.progress_file)
                .fit()
                .error(R.drawable.ic_launcher_background)
                .centerCrop()
                .into(viewHolder2.imageView); // View where image is loaded.


        return convertView;


    }
}
