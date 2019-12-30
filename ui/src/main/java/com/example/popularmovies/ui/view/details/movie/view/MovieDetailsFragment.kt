package com.example.popularmovies.ui.view.details.movie.view

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.popularmovies.R
import com.example.popularmovies.data.details.entity.cast.ActorInMovieEntity
import com.example.popularmovies.di.Injectable
import com.example.popularmovies.ui.common.scrollingthumbnail.entity.ScrollingThumbnailClickListener
import com.example.popularmovies.ui.common.scrollingthumbnail.viewmodel.ScrollingThumbnailsViewUiModel
import com.example.popularmovies.ui.view.details.movie.entity.MovieDetailsUiEntity
import com.example.popularmovies.ui.view.details.movie.viewmodel.MovieDetailsFragmentViewModel
import kotlinx.android.synthetic.main.fragment_movie_details.*
import javax.inject.Inject

class MovieDetailsFragment : Fragment(), Injectable, ScrollingThumbnailClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var fragmentViewModel: MovieDetailsFragmentViewModel
    private var showToolbarHomeBtn: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.menu_movie_details, menu)

        if (!showToolbarHomeBtn){
            menu.findItem(R.id.action_home).isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId){

            R.id.action_home -> {
                fragmentViewModel.onToolbarHomeClicked()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fragmentViewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieDetailsFragmentViewModel::class.java)

        arguments?.let {
            showToolbarHomeBtn = MovieDetailsFragmentArgs.fromBundle(it).showToolbarHomeBtn
            fragmentViewModel.start(MovieDetailsFragmentArgs.fromBundle(it).movieId)

        }

        observe()
    }

    override fun onThumbnailClicked(position: Int) {

        fragmentViewModel.onThumbnailClicked(position)
    }

    private fun initView() {

        movieDetailsActionTryAgainBtn.setOnClickListener { fragmentViewModel.onTryAgainBtnClicked() }
    }

    private fun observe() {

        fragmentViewModel.movieDetailsUiEntityLiveData.observe(this, Observer { handleEntityData(it) })
        fragmentViewModel.movieCastUiModelLiveData.observe(this, Observer { handleCastUiModelData(it) })
        fragmentViewModel.castThumbnailClickedLiveEvent.observe(this, Observer { handleThumbnailClickedEvent(it) })
        fragmentViewModel.actionHomeLiveEvent.observe(this, Observer { handleActionHomeEvent() })
        fragmentViewModel.stateLiveData.observe(this, Observer { handleStateData(it) })
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
                            fragmentViewModel.onLoadImageError(e)
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

    private fun handleThumbnailClickedEvent(actorInMovieEntity: ActorInMovieEntity) {

        val action = MovieDetailsFragmentDirections.actionDestMovieDetailsToDestPersonDetails(actorInMovieEntity.id)
        findNavController().navigate(action)
    }

    private fun handleActionHomeEvent() {

        findNavController().popBackStack(R.id.dest_main, false)
    }

    private fun handleStateData(state: MovieDetailsFragmentViewModel.STATE) {

        when(state){

            MovieDetailsFragmentViewModel.STATE.LOADING -> showLoading()

            MovieDetailsFragmentViewModel.STATE.ERROR -> showError()

            MovieDetailsFragmentViewModel.STATE.LOADED -> showLayout()
        }
    }

    /**
     * Show fragment layout only if both movie details has be received and background image has been
     * downloaded and set
     */
    private fun showLayout() {

        movieDetailsLoaderPb.visibility = View.GONE
        movieDetailsErrorViewsGroup.visibility = View.GONE
        movieDetailsLayoutViewsGroup.visibility = View.VISIBLE
    }

    private fun showLoading(){

        movieDetailsErrorViewsGroup.visibility = View.GONE
        movieDetailsLayoutViewsGroup.visibility = View.GONE
        movieDetailsLoaderPb.visibility = View.VISIBLE
    }

    private fun showError(){

        movieDetailsErrorTv.text = fragmentViewModel.userErrorMessage

        movieDetailsLoaderPb.visibility = View.GONE
        movieDetailsLayoutViewsGroup.visibility = View.GONE
        movieDetailsErrorViewsGroup.visibility = View.VISIBLE
    }

}
