package com.example.popularmovies.ui.common.scrollingthumbnail.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.popularmovies.BuildConfig
import com.example.popularmovies.R
import com.example.popularmovies.ui.common.scrollingthumbnail.entity.ThumbnailUiEntityMovieUiEntity
import com.example.popularmovies.ui.common.scrollingthumbnail.entity.ThumbnailClickListener

class ThumbnailMovieViewHolder(

        itemView: View,

        private val thumbnailClickListener: ThumbnailClickListener?

) : RecyclerView.ViewHolder(itemView) {

    private val titleTv = itemView.findViewById<TextView>(R.id.thumbnail_cast_tv_title)
    private val imageIv = itemView.findViewById<ImageView>(R.id.thumbnail_cast_iv_image)

    fun bind(thumbnailMovieUiEntity: ThumbnailUiEntityMovieUiEntity, position: Int) {

        thumbnailMovieUiEntity.let {
            titleTv.text = thumbnailMovieUiEntity.title

            Glide.with(itemView.context)
                    .load("${BuildConfig.MOVIES_IMAGE_BASE_URL}${it.imagePath}")
                    .into(imageIv)
        }

        itemView.setOnClickListener { thumbnailClickListener?.onThumbnailClicked(position) }
    }

}