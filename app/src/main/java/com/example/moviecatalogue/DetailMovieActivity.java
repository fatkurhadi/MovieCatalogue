package com.example.moviecatalogue;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moviecatalogue.helper.ModelMovie;
import com.example.moviecatalogue.helper.ModelTVShow;

import static com.example.moviecatalogue.database.DBContract.MOVIE_URI;
import static com.example.moviecatalogue.database.DBContract.tvSHOW_URI;
import static com.example.moviecatalogue.helper.ContentValueCatalogue.getContentValueMovie;
import static com.example.moviecatalogue.helper.ContentValueCatalogue.getContentValueTVShow;

public class DetailMovieActivity extends AppCompatActivity {
    public static final String EXTRA_KEY_Movie = "extra_movie";
    public static final String EXTRA_KEY_TVShow = "extra_tvshow";
    private ModelMovie modelMovie;
    private ModelTVShow modelTvShow;

    Uri detail_uri;
    Cursor detail_cursor;

    private boolean checkDetail = false, checkFavorite = false;

    private ImageView imgPosterReceived, imgBackdropReceived, btnFav;
    private TextView nameReceived, scoreReceived, scoreaverageReceived, popularityReceived, dateReceived, synopsisReceived, txtFav;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        imgBackdropReceived = findViewById(R.id.img_backdrop_received);
        imgPosterReceived = findViewById(R.id.img_poster_received);
        btnFav = findViewById(R.id.btn_fav);
        nameReceived = findViewById(R.id.name_received);
        scoreReceived = findViewById(R.id.score_received);
        scoreaverageReceived = findViewById(R.id.score_average_received);
        popularityReceived = findViewById(R.id.popularity_received);
        dateReceived = findViewById(R.id.date_received);
        synopsisReceived = findViewById(R.id.synopsis_received);
        txtFav = findViewById(R.id.txt_fav);

        if(getIntent().getParcelableExtra(EXTRA_KEY_Movie)!= null){
            checkDetail = true;
            detail_uri = getIntent().getData();
            detail_cursor = getContentResolver().query(detail_uri, null, null, null, null);
            if (detail_cursor != null){
                if (detail_cursor.moveToFirst()) {
                    modelMovie = new ModelMovie(detail_cursor);
                    detail_cursor.close();
                }
            }
            if (modelMovie != null){
                checkFavorite = true;
            } else {
                checkFavorite = false;
                modelMovie = getIntent().getParcelableExtra(EXTRA_KEY_Movie);
            }
            setMovieDetails();
        } else {
            checkDetail = false;
            detail_uri = getIntent().getData();
            detail_cursor = getContentResolver().query(detail_uri, null, null, null, null);
            if (detail_cursor != null){
                if (detail_cursor.moveToFirst()) {
                    modelTvShow = new ModelTVShow(detail_cursor);
                    detail_cursor.close();
                }
            }
            if (modelTvShow != null) {
                checkFavorite = true;
            } else {
                checkFavorite = false;
                modelTvShow = getIntent().getParcelableExtra(EXTRA_KEY_TVShow);
            }
            setTVShowDetails();
        }

        favChange();
        btnFav.setOnClickListener(favListen);
    }

    private View.OnClickListener favListen = view -> {
        if (!checkFavorite) {
            if (checkDetail){
                checkFavorite = true;
                ContentValues values = getContentValueMovie(modelMovie);
                getContentResolver().insert(MOVIE_URI, values);
                Toast.makeText(this, modelMovie.getMovie_name() + " " + getString(R.string.added), Toast.LENGTH_SHORT).show();
                favChange();
            } else {
                checkFavorite = true;
                ContentValues values = getContentValueTVShow(modelTvShow);
                getContentResolver().insert(tvSHOW_URI, values);
                Toast.makeText(this, modelTvShow.getTvshow_name() + " " + getString(R.string.added), Toast.LENGTH_SHORT).show();
                favChange();
            }
        } else {
            if (checkDetail) {
                checkFavorite = false;
                getContentResolver().delete(detail_uri, null, null);
                Toast.makeText(this, modelMovie.getMovie_name() + " " + getString(R.string.deleted), Toast.LENGTH_SHORT).show();
                favChange();
            } else {
                checkFavorite = false;
                getContentResolver().delete(detail_uri, null, null);
                Toast.makeText(this, modelTvShow.getTvshow_name() + " " + getString(R.string.deleted), Toast.LENGTH_SHORT).show();
                favChange();
            }
        }
    };

    private void setTVShowDetails() {
        Glide.with(this)
                .load(modelTvShow.getTvshow_backdrop())
                .into(imgBackdropReceived);
        Glide.with(this)
                .load(modelTvShow.getTvshow_poster())
                .into(imgPosterReceived);
        nameReceived.setText(modelTvShow.getTvshow_name());
        dateReceived.setText(modelTvShow.getTvshow_date());
        scoreReceived.setText(modelTvShow.getTvshow_score());
        scoreaverageReceived.setText(modelTvShow.getTvshow_score_average());
        popularityReceived.setText(modelTvShow.getTvshow_popularity());
        synopsisReceived.setText(modelTvShow.getTvshow_synopsis());
    }

    private void setMovieDetails() {
        Glide.with(this)
                .load(modelMovie.getMovie_backdrop())
                .into(imgBackdropReceived);
        Glide.with(this)
                .load(modelMovie.getMovie_poster())
                .into(imgPosterReceived);
        nameReceived.setText(modelMovie.getMovie_name());
        dateReceived.setText(modelMovie.getMovie_date());
        scoreReceived.setText(modelMovie.getMovie_score());
        scoreaverageReceived.setText(modelMovie.getMovie_score_average());
        popularityReceived.setText(modelMovie.getMovie_popularity());
        synopsisReceived.setText(modelMovie.getMovie_synopsis());
    }

    private void favChange(){
        if (checkFavorite) {
            Glide.with(this)
                    .load("")
                    .placeholder(R.drawable.ic_favorite)
                    .into(btnFav);
            txtFav.setText(getString(R.string.fav_delete));
        } else {
            Glide.with(this)
                    .load("")
                    .placeholder(R.drawable.ic_favorite_border)
                    .into(btnFav);
            txtFav.setText(getString(R.string.fav_add));
        }
    }
}
