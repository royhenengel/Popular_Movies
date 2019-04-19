package com.example.popularmovies.ui.details.person.view

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.popularmovies.data.details.entity.movie.MovieActorInEntity
import com.example.popularmovies.di.Injectable
import com.example.popularmovies.ui.common.scrollingthumbnail.entity.ScrollingThumbnailClickListener
import com.example.popularmovies.ui.common.scrollingthumbnail.viewmodel.ScrollingThumbnailsViewUiModel
import com.example.popularmovies.ui.details.movie.view.MovieDetailsFragmentDirections
import com.example.popularmovies.ui.details.person.entity.PersonDetailsUiEntity
import com.example.popularmovies.ui.details.person.viewmodel.PersonDetailsFragmentViewModel
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.android.synthetic.main.fragment_person_details.*
import javax.inject.Inject

class PersonDetailsFragment : Fragment(), Injectable, ScrollingThumbnailClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var fragmentFragmentViewModel: PersonDetailsFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_person_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fragmentFragmentViewModel = ViewModelProviders.of(this, viewModelFactory).get(PersonDetailsFragmentViewModel::class.java)

        arguments?.let {
            fragmentFragmentViewModel.start(PersonDetailsFragmentArgs.fromBundle(it).personId)
        }

        observe()
    }

    override fun onThumbnailClicked(position: Int) {

        fragmentFragmentViewModel.onThumbnailClicked(position)
    }

    private fun observe() {

        fragmentFragmentViewModel.personDetailsUiEntityLiveData.observe(this, Observer { handlePersonDetailsData(it) })
        fragmentFragmentViewModel.movieThumbnailsUiModelLiveData.observe(this, Observer { handleThumbnailsData(it) })
        fragmentFragmentViewModel.movieActorInClickedLiveEvent.observe(this, Observer { handleActorInMovieClickedEvent(it) })
    }

    private fun handlePersonDetailsData(personDetailsUiEntity: PersonDetailsUiEntity) {

        personDetailsUiEntity.let {
            personDetailsNameTv.text = it.name
            personDetailsBirthdayTv.text = it.lifeExpectancy
            personDetailsBiographyTv.text = it.biography

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
                            personDetailsImageIv.background = resource
                            showLayout()
                            return true
                        }
                    })
                    .into(personDetailsImageIv)
        }
    }

    private fun handleThumbnailsData(uiModel: ScrollingThumbnailsViewUiModel) {

        personDetailsMoviesScrollingThumbnailsV.setClickListener(this)
        personDetailsMoviesScrollingThumbnailsV.setUiModel(uiModel, this)
    }

    private fun handleActorInMovieClickedEvent(movieActorInEntity: MovieActorInEntity) {

        val action = PersonDetailsFragmentDirections.actionDestPersonDetailsToDestMovieDetails(movieActorInEntity.id)
        findNavController().navigate(action)
    }

    /** TODO - Show all the layout only when both terms are met
     * Show fragment layout only if both movie details has be received and background image has been
     * downloaded and set
     */
    private fun showLayout() {

        personDetailsLoaderPb.visibility = View.GONE
        personDetailsViewsGroup.visibility = View.VISIBLE
    }

}
