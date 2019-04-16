package com.example.popularmovies.ui.common.scrollingthumbnail.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.popularmovies.ui.common.scrollingthumbnail.model.Thumbnail
import com.example.popularmovies.ui.common.scrollingthumbnail.model.ThumbnailClickListener
import com.example.popularmovies.viewmodel.SingleLiveEvent

class ScrollingThumbnailsViewUiModel @JvmOverloads constructor(

    thumbnails: List<Thumbnail>? = null

): ThumbnailClickListener {

    val thumbnailsLiveData = MutableLiveData<List<Thumbnail>>()
    val clickListenerLiveEvent = SingleLiveEvent<Int>()

    init {

        thumbnails?.let {
            thumbnailsLiveData.value = it
        }
    }

    override fun onThumbnailClicked(position: Int) {

        clickListenerLiveEvent.value = position
    }

    fun setThumbnails(thumbnails: List<Thumbnail>){

        thumbnailsLiveData.value = thumbnails
    }

}