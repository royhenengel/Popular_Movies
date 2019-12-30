package com.example.popularmovies.ui.common.scrollingthumbnail.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.example.popularmovies.ui.BuildConfig
import com.example.popularmovies.ui.R
import com.example.popularmovies.ui.common.scrollingthumbnail.entity.ThumbnailClickListener
import com.example.popularmovies.ui.common.scrollingthumbnail.entity.ThumbnailUiEntity

class ThumbnailViewHolder(

    itemView: View,

    private val thumbnailClickListener: ThumbnailClickListener?,

    private val glideRequestManager: RequestManager

) : RecyclerView.ViewHolder(itemView) {

    private val titleTv = itemView.findViewById<TextView>(R.id.thumbnail_tv_title)
    private val descTv = itemView.findViewById<TextView>(R.id.thumbnail_tv_desc)
    private val imageIv = itemView.findViewById<ImageView>(R.id.thumbnail_iv_image)

    fun bind(thumbnailCastUiEntity: ThumbnailUiEntity, position: Int) {

        thumbnailCastUiEntity.let {

            glideRequestManager.load("${BuildConfig.MOVIES_IMAGE_BASE_URL}${it.imagePath}")
                .apply(initRequestOptions())
                .into(imageIv)

            titleTv.text = it.title

            if (it.desc != null) {
                descTv.text = it.desc
            } else {
                descTv.visibility = View.GONE
            }
        }

        itemView.setOnClickListener { thumbnailClickListener?.onThumbnailClicked(position) }
    }

    private fun initRequestOptions(): RequestOptions {

        return RequestOptions().apply(
            RequestOptions.errorOf(R.drawable.placeholder_profile_photo)
        )
    }

}