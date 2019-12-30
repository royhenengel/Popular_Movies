package com.example.popularmovies.ui.common.scrollingthumbnail.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.popularmovies.ui.common.scrollingthumbnail.entity.ThumbnailClickListener
import com.example.popularmovies.ui.common.scrollingthumbnail.entity.ThumbnailUiEntity
import com.example.popularmovies.ui.viewmodel.SingleLiveEvent

class ScrollingThumbnailsViewUiModel @JvmOverloads constructor(

        thumbnailUiEntities: List<ThumbnailUiEntity>? = null

): ThumbnailClickListener {

    val thumbnailsLiveData = MutableLiveData<List<ThumbnailUiEntity>>()
    val clickListenerLiveEvent =
        com.example.popularmovies.ui.viewmodel.SingleLiveEvent<Int>()

    init {

        thumbnailUiEntities?.let {
            thumbnailsLiveData.value = it
        }
    }

    override fun onThumbnailClicked(position: Int) {

        clickListenerLiveEvent.value = position
    }

    fun setThumbnails(thumbnailUiEntities: List<ThumbnailUiEntity>){

        thumbnailsLiveData.value = thumbnailUiEntities
    }

}