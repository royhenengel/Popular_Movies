package com.example.popularmovies.ui.common.scrollingthumbnail.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.popularmovies.ui.common.scrollingthumbnail.model.Thumbnail
import com.example.popularmovies.ui.common.scrollingthumbnail.model.ThumbnailCast
import com.example.popularmovies.ui.common.scrollingthumbnail.model.ThumbnailClickListener
import com.example.popularmovies.ui.common.scrollingthumbnail.model.ThumbnailMovie
import com.example.popularmovies.ui.common.scrollingthumbnail.viewmodel.ScrollingThumbnailsViewUiModel

class ScrollingThumbnailsView @JvmOverloads constructor(

        context: Context,

        attrs: AttributeSet? = null,

        defStyleAttr: Int = 0

) : RecyclerView(context, attrs, defStyleAttr) {

    private lateinit var lifecycleOwner: LifecycleOwner
    private lateinit var uiModel: ScrollingThumbnailsViewUiModel

    init {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        setHasFixedSize(true)

        background = null
    }

    fun setUiModel(scrollingThumbnailsViewUiModel: ScrollingThumbnailsViewUiModel, lifecycleOwner: LifecycleOwner) {

        this.lifecycleOwner = lifecycleOwner
        this.uiModel = scrollingThumbnailsViewUiModel

        observe()
    }

    fun setClickLlistener(thumbnailsClickListener: ThumbnailClickListener){

        uiModel.clickListener = thumbnailsClickListener
    }

    private fun observe() {

        uiModel.thumbnailsLiveData.observe(lifecycleOwner, Observer { handleThumbnailsData(it) })
    }

    private fun handleThumbnailsData(thumbnails: List<Thumbnail>) {

        adapter = ScrollingThumbnailAdapter(thumbnails, uiModel)
    }

}