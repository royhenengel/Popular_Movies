package com.example.popularmovies.ui.main.views

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.popularmovies.BuildConfig
import com.example.popularmovies.R
import com.example.popularmovies.data.main.models.MovieModel
import com.example.popularmovies.util.dateAsString

class MovieViewHolder(

    itemView: View,

    private val movieClickListener: MovieClickListener?

) : RecyclerView.ViewHolder(itemView) {

    private val title: TextView = itemView.findViewById(R.id.item_movie_tv_title)
    private val yearTv: TextView = itemView.findViewById(R.id.item_movie_tv_year)
    private val overviewTv: TextView = itemView.findViewById(R.id.item_movie_tv_overview)
    private val scoreTv: TextView = itemView.findViewById(R.id.item_movie_tv_score)
    private val thumbnailIv: ImageView = itemView.findViewById(R.id.item_movie_iv_image)

    interface MovieClickListener{
        fun onMovieClicked(position: Int)
    }

    fun bind(movieModel: MovieModel?, position: Int){

        movieModel?.let {
            title.text = it.title
            yearTv.text = dateAsString(it.releaseDate, PATTERN_YEAR)
            overviewTv.text = it.overview
            scoreTv.text = it.voteAverage.toString()

            if(it.posterPath != null){
                Glide.with(thumbnailIv.context)
                    .load("${BuildConfig.MOVIES_IMAGE_BASE_URL}${it.posterPath}")
                    .into(thumbnailIv)
            }
            else{
                Glide.with(thumbnailIv.context)
                    .clear(thumbnailIv)
            }

            itemView.setOnClickListener { movieClickListener?.onMovieClicked(position) }
        }
    }

    companion object {
        private const val PATTERN_YEAR = "yyyy"
    }
}