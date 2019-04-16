package com.example.popularmovies.ui.main.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.popularmovies.R
import com.example.popularmovies.data.main.models.MovieEntity


class MainMoviesAdapter(

    private val movieClickListener: MovieViewHolder.MovieClickListener

) : PagedListAdapter<MovieEntity, MovieViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main_movie, parent, false)
        return MovieViewHolder(view, movieClickListener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        holder.bind(getItem(position), position)
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