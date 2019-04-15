package com.example.popularmovies.ui.common.scrollingthumbnail.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.popularmovies.ui.common.scrollingthumbnail.model.Thumbnail
import com.example.popularmovies.ui.common.scrollingthumbnail.model.ThumbnailClickListener

class ScrollingThumbnailsViewUiModel @JvmOverloads constructor(

    thumbnails: List<Thumbnail>? = null

): ThumbnailClickListener {

    val thumbnailsLiveData = MutableLiveData<List<Thumbnail>>()

    var clickListener: ThumbnailClickListener? = null

    init {

        thumbnails?.let {
            thumbnailsLiveData.value = it
        }
    }

    override fun onThumbnailClicked(position: Int) {

        clickListener?.onThumbnailClicked(position)
    }

    fun setThumbnails(thumbnails: List<Thumbnail>){

        thumbnailsLiveData.value = thumbnails
    }

}