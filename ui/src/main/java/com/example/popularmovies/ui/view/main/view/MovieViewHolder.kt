package com.example.popularmovies.ui.view.main.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.popularmovies.ui.R
import com.example.popularmovies.ui.view.main.model.MovieUiModel

class MovieViewHolder(

    itemView: View,

    private val movieClickListener: MovieClickListener?,

    private val glide: RequestManager

) : RecyclerView.ViewHolder(itemView) {

    private val title: TextView = itemView.findViewById(R.id.item_movie_tv_title)
    private val yearTv: TextView = itemView.findViewById(R.id.item_movie_tv_year)
    private val overviewTv: TextView = itemView.findViewById(R.id.item_movie_tv_overview)
    private val scoreTv: TextView = itemView.findViewById(R.id.item_movie_tv_score)
    private val thumbnailIv: ImageView = itemView.findViewById(R.id.item_movie_iv_image)

    interface MovieClickListener {
        fun onMovieClicked(uiModel: MovieUiModel)
    }

    fun bind(movieModel: MovieUiModel?) {

        movieModel?.let {
            title.text = it.title
            yearTv.text = it.releaseDate
            overviewTv.text = it.overview
            scoreTv.text = it.score.toString()

            glide.load("$MOVIES_IMAGE_BASE_URL${it.thumbnailPath}").into(thumbnailIv)

            itemView.setOnClickListener { movieClickListener?.onMovieClicked(movieModel) }
        }
    }

}