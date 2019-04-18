package com.example.popularmovies.ui.common.scrollingthumbnail.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.popularmovies.BuildConfig
import com.example.popularmovies.R
import com.example.popularmovies.ui.common.scrollingthumbnail.entity.ThumbnailUiEntity
import com.example.popularmovies.ui.common.scrollingthumbnail.entity.ThumbnailClickListener

class ThumbnailViewHolder(

        itemView: View,

        private val thumbnailClickListener: ThumbnailClickListener?

) : RecyclerView.ViewHolder(itemView) {

    private val titleTv = itemView.findViewById<TextView>(R.id.thumbnail_tv_title)
    private val descTv = itemView.findViewById<TextView>(R.id.thumbnail_tv_desc)
    private val imageIv = itemView.findViewById<ImageView>(R.id.thumbnail_iv_image)

    fun bind(thumbnailCastUiEntity: ThumbnailUiEntity, position: Int) {

        thumbnailCastUiEntity.let {
            titleTv.text = it.title
            if (it.desc != null){
                descTv.text = it.desc
            }
            else {
                descTv.visibility = View.GONE
            }

            Glide.with(itemView.context)
                    .load("${BuildConfig.MOVIES_IMAGE_BASE_URL}${it.imagePath}")
                    .into(imageIv)
        }

        itemView.setOnClickListener { thumbnailClickListener?.onThumbnailClicked(position) }
    }

}