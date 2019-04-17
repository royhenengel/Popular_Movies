package com.example.popularmovies.ui.details.movie.view

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.popularmovies.R
import com.example.popularmovies.data.details.entity.cast.CastEntity
import com.example.popularmovies.di.Injectable
import com.example.popularmovies.ui.common.scrollingthumbnail.entity.ScrollingThumbnailClickListener
import com.example.popularmovies.ui.common.scrollingthumbnail.viewmodel.ScrollingThumbnailsViewUiModel
import com.example.popularmovies.ui.details.movie.entity.MovieDetailsUiEntity
import com.example.popularmovies.ui.details.movie.viewmodel.MovieDetailsFragmentViewModel
import kotlinx.android.synthetic.main.fragment_movie_details.*
import javax.inject.Inject

class MovieDetailsFragment : Fragment(), Injectable, ScrollingThumbnailClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var fragmentViewModel: MovieDetailsFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fragmentViewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieDetailsFragmentViewModel::class.java)

        arguments?.let {
            fragmentViewModel.start(MovieDetailsFragmentArgs.fromBundle(it).movieId)

        }

        observe()
    }

    override fun onThumbnailClicked(position: Int) {

        fragmentViewModel.onThumbnailClicked(position)
    }

    private fun observe() {

        fragmentViewModel.movieDetailsUiEntityLiveData.observe(this, Observer { handleEntityData(it) })
        fragmentViewModel.movieCastUiModelLiveData.observe(this, Observer { handleCastUiModelData(it) })
        fragmentViewModel.castThumbnailClickedLiveEvent.observe(this, Observer { handleThumbnailClickedEvent(it) })
    }

    private fun handleEntityData(uiEntity: MovieDetailsUiEntity) {

        uiEntity.let {
            titleTv.text = it.title
            yearTv.text = it.title
            overviewTv.text = it.overview
            scoreTv.text = it.score
            lengthTv.text = it.length

            Glide.with(context!!)
                    .load(it.imageUrl)
                    .listener(object : RequestListener<Drawable> {

                        override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                        ): Boolean {
                            // TODO Handle error loading image
                            return true
                        }

                        override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                        ): Boolean {
                            imageBackgroundIv.background = resource
                            showLayout()
                            return true
                        }
                    })
                    .into(imageBackgroundIv)
        }
    }

    private fun handleCastUiModelData(castUiModel: ScrollingThumbnailsViewUiModel) {

        castScrollingThumbnailsV.setClickListener(this)
        castScrollingThumbnailsV.setUiModel(castUiModel, this)
    }

    private fun handleThumbnailClickedEvent(castEntity: CastEntity) {

        Toast.makeText(context, "${castEntity.name} clicked", Toast.LENGTH_LONG).show()
    }

    /** TODO - Show all the layout only when both terms are met
     * Show fragment layout only if both movie details has be received and background image has been
     * downloaded and set
     */
    private fun showLayout() {

        loaderPb.visibility = View.GONE
        movieDetailsViewsGroup.visibility = View.VISIBLE
    }

    companion object {

        private const val MESSAGE_ARGS_NOT_RECEIVED = "Movie model wan't received, can't start fragment"
    }

}
