package com.example.popularmovies.ui.main.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.popularmovies.BuildConfig
import com.example.popularmovies.R
import com.example.popularmovies.ui.main.entity.MovieUiEntity

class MovieViewHolder(

    itemView: View,

    private val movieClickListener: MovieClickListener?

) : RecyclerView.ViewHolder(itemView) {

    private val title: TextView = itemView.findViewById(R.id.item_movie_tv_title)
    private val yearTv: TextView = itemView.findViewById(R.id.item_movie_tv_year)
    private val overviewTv: TextView = itemView.findViewById(R.id.item_movie_tv_overview)
    private val scoreTv: TextView = itemView.findViewById(R.id.item_movie_tv_score)
    private val thumbnailIv: ImageView = itemView.findViewById(R.id.item_movie_iv_image)

    interface MovieClickListener {
        fun onMovieClicked(position: Int)
    }

    fun bind(movieEntity: MovieUiEntity?, position: Int) {

        movieEntity?.let {
            title.text = it.title
            yearTv.text = it.releaseDate
            overviewTv.text = it.overview
            scoreTv.text = it.score.toString()

            Glide.with(thumbnailIv.context)
                .load("${BuildConfig.MOVIES_IMAGE_BASE_URL}${it.thumbnailPath}")
                .into(thumbnailIv)

            itemView.setOnClickListener { movieClickListener?.onMovieClicked(position) }
        }
    }

}