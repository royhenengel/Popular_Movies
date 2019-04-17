package com.example.popularmovies.ui.common.scrollingthumbnail.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.popularmovies.R
import com.example.popularmovies.ui.common.scrollingthumbnail.entity.*

class ScrollingThumbnailAdapter(

        private val dataSet: List<ThumbnailUiEntity>,

        private val thumbnailClickListener: ThumbnailClickListener

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {

            TYPE_CAST -> createCastViewHolder(parent)

            TYPE_MOVIE -> createMovieViewHolder(parent)

            TYPE_NOT_VALID -> createMovieViewHolder(parent)

            else -> createMovieViewHolder(parent)
        }
    }

    override fun getItemCount(): Int {

        return dataSet.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (getItemViewType(position)) {

            TYPE_CAST ->
                bindCastViewHolder(holder = holder as ThumbnailCastViewHolder, position = position)

            TYPE_MOVIE ->
                bindMovieViewHolder(holder = holder as ThumbnailMovieViewHolder, position = position)
        }
    }

    override fun getItemViewType(position: Int): Int {

        return when (dataSet[position]) {

            is ThumbnailUiEntityCastUiEntity -> TYPE_CAST

            is ThumbnailUiEntityMovieUiEntity -> TYPE_MOVIE

            else -> TYPE_NOT_VALID
        }
    }

    private fun createMovieViewHolder(parent: ViewGroup): ThumbnailMovieViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_thumbnail_movie, parent, false)
        return ThumbnailMovieViewHolder(itemView = view, thumbnailClickListener = thumbnailClickListener)
    }

    private fun createCastViewHolder(parent: ViewGroup): ThumbnailCastViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_thumbnail_cast, parent, false)
        return ThumbnailCastViewHolder(itemView = view, thumbnailClickListener = thumbnailClickListener)
    }

    private fun bindCastViewHolder(holder: ThumbnailCastViewHolder, position: Int) {

        val thumbnailCastUiEntity: ThumbnailUiEntityCastUiEntity = dataSet[position] as ThumbnailUiEntityCastUiEntity
        holder.bind(thumbnailCastUiEntity, position)
    }

    private fun bindMovieViewHolder(holder: ThumbnailMovieViewHolder, position: Int) {

        val thumbnailMovieUiEntity: ThumbnailUiEntityMovieUiEntity = dataSet[position] as ThumbnailUiEntityMovieUiEntity
        holder.bind(thumbnailMovieUiEntity, position)
    }

    companion object {

        private const val TYPE_NOT_VALID = -1
        private const val TYPE_CAST = 1
        private const val TYPE_MOVIE = 2
    }

}