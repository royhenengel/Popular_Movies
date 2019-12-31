package com.example.popularmovies.ui.view.main.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.RequestManager
import com.example.popularmovies.R
import com.example.popularmovies.data.main.entity.MovieEntity
import com.example.popularmovies.ui.view.main.model.mapper.MovieUiModelMapper


class MainMoviesAdapter(

    private val movieUiModelMapper: MovieUiModelMapper,

    private val movieClickListener: MovieViewHolder.MovieClickListener,

    private val glideRequestManager: RequestManager

) : PagedListAdapter<MovieEntity, MovieViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main_movie, parent, false)
        return MovieViewHolder(
            view,
            movieClickListener,
            glideRequestManager
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        val uiEntity = movieUiModelMapper.apply(getItem(position)!!)
        holder.bind(uiEntity)
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {

            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

}