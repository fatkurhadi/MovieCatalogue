package com.example.moviecatalogue.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviecatalogue.DetailMovieActivity;
import com.example.moviecatalogue.helper.ModelMovie;
import com.example.moviecatalogue.R;

import java.util.ArrayList;

import static com.example.moviecatalogue.database.DBContract.MOVIE_URI;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ModelMovie> listModelMovie = new ArrayList<>();
    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public  MovieAdapter (Context context) {
        this.context = context;
    }

    public void setData(ArrayList<ModelMovie> modelMovies){
        listModelMovie.clear();
        listModelMovie.addAll(modelMovies);
        notifyDataSetChanged();
    }

    public ArrayList<ModelMovie> getListData(){
        return listModelMovie;
    }

    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int positionm) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_catalogue, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int positionm) {
        ModelMovie modelMovie = listModelMovie.get(positionm);
        Glide.with(context)
                .load(modelMovie.getMovie_poster())
                .into(holder.imgPoster);
        Glide.with(context)
                .load(modelMovie.getMovie_backdrop())
                .into(holder.imgBackdrop);
        holder.txtName.setText(modelMovie.getMovie_name());
        holder.txtDate.setText(modelMovie.getMovie_date());
        holder.txtScoreAverage.setText(modelMovie.getMovie_score_average());

        holder.itemView.setOnClickListener(v -> {
            onItemClickCallback.onItemClicked(listModelMovie.get(holder.getAdapterPosition()));
            Uri uriMovieAdapter = Uri.parse(MOVIE_URI + "/" + listModelMovie.get(holder.getAdapterPosition()).getMovie_id());
            Intent movieMoveIntent = new Intent(holder.itemView.getContext(), DetailMovieActivity.class);
            movieMoveIntent.setData(uriMovieAdapter);
            movieMoveIntent.putExtra(DetailMovieActivity.EXTRA_KEY_Movie, listModelMovie.get(holder.getAdapterPosition()));
            holder.itemView.getContext().startActivity(movieMoveIntent);
        });
    }

    @Override
    public int getItemCount() {
        return listModelMovie.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster,imgBackdrop;
        TextView txtName, txtDate, txtScoreAverage;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.item_poster);
            imgBackdrop = itemView.findViewById(R.id.item_backdrop);
            txtName = itemView.findViewById(R.id.item_name);
            txtDate = itemView.findViewById(R.id.item_date);
            txtScoreAverage = itemView.findViewById(R.id.item_score_average);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(ModelMovie modelMovie);
    }
}
