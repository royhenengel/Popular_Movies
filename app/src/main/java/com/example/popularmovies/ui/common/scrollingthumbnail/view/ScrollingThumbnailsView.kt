package com.example.popularmovies.ui.common.scrollingthumbnail.view

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.popularmovies.ui.common.scrollingthumbnail.entity.ScrollingThumbnailClickListener
import com.example.popularmovies.ui.common.scrollingthumbnail.entity.ThumbnailUiEntity
import com.example.popularmovies.ui.common.scrollingthumbnail.viewmodel.ScrollingThumbnailsViewUiModel

class ScrollingThumbnailsView @JvmOverloads constructor(

        context: Context,

        attrs: AttributeSet? = null,

        defStyleAttr: Int = 0

) : RecyclerView(context, attrs, defStyleAttr) {

    private lateinit var lifecycleOwner: LifecycleOwner
    private lateinit var uiModel: ScrollingThumbnailsViewUiModel

    private var thumbnailClickListener: ScrollingThumbnailClickListener? = null

    init {
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        background = null
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        // Clear view instance from live data observers array to avoid memory leaks due to usage of same
        // LifecycleOwner object as the fragment
        uiModel.thumbnailsLiveData.removeObservers(lifecycleOwner)
        uiModel.clickListenerLiveEvent.removeObservers(lifecycleOwner)
    }

    fun setUiModel(scrollingThumbnailsViewUiModel: ScrollingThumbnailsViewUiModel, lifecycleOwner: LifecycleOwner) {

        this.lifecycleOwner = lifecycleOwner
        this.uiModel = scrollingThumbnailsViewUiModel

        observe()
    }

    fun setClickListener(scrollingThumbnailClickListener: ScrollingThumbnailClickListener){

        thumbnailClickListener = scrollingThumbnailClickListener
    }

    private fun observe() {

        uiModel.thumbnailsLiveData.observe(lifecycleOwner, Observer { handleThumbnailsData(it) })
        uiModel.clickListenerLiveEvent.observe(lifecycleOwner, Observer { handleThumbnailClickedEvent(it) })
    }

    private fun handleThumbnailsData(thumbnailUiEntities: List<ThumbnailUiEntity>) {

        adapter = ScrollingThumbnailAdapter(thumbnailUiEntities, uiModel, Glide.with(this))
    }

    private fun handleThumbnailClickedEvent(positing: Int) {

        thumbnailClickListener?.onThumbnailClicked(positing)
    }

}