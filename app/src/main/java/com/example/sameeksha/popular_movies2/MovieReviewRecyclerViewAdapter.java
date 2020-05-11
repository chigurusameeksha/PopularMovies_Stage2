package com.example.sameeksha.popular_movies2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MovieReviewRecyclerViewAdapter extends RecyclerView.Adapter<MovieReviewRecyclerViewAdapter.CustomViewHolder> {
    private ArrayList<MovieReviews> arrayList;
    private Context context;
    private LayoutInflater inflater;

    public MovieReviewRecyclerViewAdapter(Context context, ArrayList<MovieReviews>arrayList) {
        this.arrayList= arrayList;
        this.context = context;
        inflater= LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.single_row_review_item,parent ,false );

        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.author_textView.setText(arrayList.get(position).getAuthor());
        holder.content_textview.setText(arrayList.get(position).getContents());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder{
        TextView author_textView;
        TextView content_textview;


        public CustomViewHolder(View itemView) {
            super(itemView);

            this.author_textView = itemView.findViewById(R.id.review_author);
            this.content_textview = itemView.findViewById(R.id.review_contents);


        }
    }
}
