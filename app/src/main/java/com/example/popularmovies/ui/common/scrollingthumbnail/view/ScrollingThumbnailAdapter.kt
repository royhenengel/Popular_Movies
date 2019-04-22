package com.example.popularmovies.ui.common.scrollingthumbnail.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.popularmovies.R
import com.example.popularmovies.ui.common.scrollingthumbnail.entity.ThumbnailClickListener
import com.example.popularmovies.ui.common.scrollingthumbnail.entity.ThumbnailUiEntity

class ScrollingThumbnailAdapter(

    private val dataSet: List<ThumbnailUiEntity>,

    private val thumbnailClickListener: ThumbnailClickListener

) : RecyclerView.Adapter<ThumbnailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThumbnailViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_thumbnail, parent, false)
        return ThumbnailViewHolder(itemView = view, thumbnailClickListener = thumbnailClickListener)
    }

    override fun getItemCount(): Int {

        return dataSet.size
    }

    override fun onBindViewHolder(holder: ThumbnailViewHolder, position: Int) {

        val thumbnailCastUiEntity: ThumbnailUiEntity = dataSet[position]
        holder.bind(thumbnailCastUiEntity, position)
    }

}