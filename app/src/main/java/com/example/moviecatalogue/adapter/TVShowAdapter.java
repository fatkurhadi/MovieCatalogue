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
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.helper.ModelTVShow;

import java.util.ArrayList;

import static com.example.moviecatalogue.database.DBContract.tvSHOW_URI;

public class TVShowAdapter extends RecyclerView.Adapter<TVShowAdapter.ViewHolder>{
    private Context context;
    private ArrayList<ModelTVShow> listModelTVShow = new ArrayList<>();
    private TVShowAdapter.OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public  TVShowAdapter (Context context) {
        this.context = context;
    }

    public void setData(ArrayList<ModelTVShow> modelTvShows){
        listModelTVShow.clear();
        listModelTVShow.addAll(modelTvShows);
        notifyDataSetChanged();
    }

    public ArrayList<ModelTVShow> getListData(){
        return listModelTVShow;
    }

    @NonNull
    @Override
    public TVShowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int positiontv) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_catalogue, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int positiontv) {
        ModelTVShow modelTvShow = listModelTVShow.get(positiontv);
        Glide.with(context)
                .load(modelTvShow.getTvshow_poster())
                .into(holder.imgPoster);
        Glide.with(context)
                .load(modelTvShow.getTvshow_backdrop())
                .into(holder.imgBackdrop);
        holder.txtName.setText(modelTvShow.getTvshow_name());
        holder.txtDate.setText(modelTvShow.getTvshow_date());
        holder.txtScoreAverage.setText(modelTvShow.getTvshow_score_average());

        holder.itemView.setOnClickListener(v -> {
            onItemClickCallback.onItemClicked(listModelTVShow.get(holder.getAdapterPosition()));
            Uri uriTVShowAdapter = Uri.parse(tvSHOW_URI + "/" + listModelTVShow.get(holder.getAdapterPosition()).getTvshow_id());
            Intent tvShowMoveIntent = new Intent(holder.itemView.getContext(), DetailMovieActivity.class);
            tvShowMoveIntent.setData(uriTVShowAdapter);
            tvShowMoveIntent.putExtra(DetailMovieActivity.EXTRA_KEY_TVShow, listModelTVShow.get(holder.getAdapterPosition()));
            holder.itemView.getContext().startActivity(tvShowMoveIntent);
        });
    }

    @Override
    public int getItemCount() {
        return listModelTVShow.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster, imgBackdrop;
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
        void onItemClicked(ModelTVShow modelTvShow);
    }
}
