package com.example.sameeksha.popular_movies2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MovieTrailerRecyclerViewAdapter extends RecyclerView.Adapter<MovieTrailerRecyclerViewAdapter.CustomViewHolder> {
    private ArrayList<MovieTrailer> videoArrayList = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;
    ArrayList<MediaStore.Video> videos = new ArrayList<>();


    public MovieTrailerRecyclerViewAdapter(Context context, ArrayList<MovieTrailer> videoArrayList) {
        this.context = context;
        this.videoArrayList = videoArrayList;
        inflater= LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //created once and pass to viewHolder to be held for future used and avoid the expensive inflater operation
        View view = inflater.inflate(R.layout.single_row_trailer_item,parent , false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        // pass the view held by customViewHolder class.
        Uri uri= Uri.parse(videoArrayList.get(position).getTrailer());
        String videoId= uri.getQueryParameter("v"); // the video key/video from the passed url
        String url =  "https://img.youtube.com/vi/"+videoId +"/default.jpg";
        Glide.with(context)
                .load(url)
                .into(holder.trailerImage);



        holder.trailerTextView.setText(videoArrayList.get(position).getTrailerTitle());

    }

    @Override
    public int getItemCount() {
        // the number of items passed to be recycled

        return videoArrayList.size();

    }



    class CustomViewHolder extends RecyclerView.ViewHolder {
        // single rows views held
        ImageView trailerImage;
        TextView trailerTextView;



        public CustomViewHolder(View itemView) {
            super(itemView);
            this.trailerImage = itemView.findViewById(R.id.trailer_image);
           this.trailerTextView = itemView.findViewById(R.id.trailer_textview);

           trailerImage.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   int positon = getLayoutPosition();
                   if(positon!=RecyclerView.NO_POSITION){
                       Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(videoArrayList.get(positon).getTrailer()));
                       context.startActivity(intent);
                   }

               }
           });


            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int positon = getLayoutPosition();
                    if(positon!=RecyclerView.NO_POSITION){
                        Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(videoArrayList.get(positon).getTrailer()));
                        context.startActivity(intent);
                    }
                    //Toast.makeText(context,"item clikced here"+ positon ,Toast.LENGTH_LONG).show();

                }
            });
*/
        }


    }
}
