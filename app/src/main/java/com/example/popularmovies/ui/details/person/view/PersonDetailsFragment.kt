package com.example.popularmovies.ui.details.person.view

import android.os.Bundle
import android.renderscript.Allocation
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.popularmovies.R
import com.example.popularmovies.data.details.entity.movie.MovieActorInEntity
import com.example.popularmovies.ui.common.scrollingthumbnail.viewmodel.ScrollingThumbnailsViewUiModel
import com.example.popularmovies.ui.details.person.entity.PersonDetailsUiEntity
import com.example.popularmovies.ui.details.person.viewmodel.PersonDetailsViewModel

class PersonDetailsFragment : Fragment() {

    private lateinit var fragmentViewModel: PersonDetailsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_person_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fragmentViewModel = ViewModelProviders.of(this).get(PersonDetailsViewModel::class.java)

        arguments?.let {
            fragmentViewModel.start(PersonDetailsFragmentArgs.fromBundle(it).personId)
        }

        observe()
    }

    private fun observe() {

        fragmentViewModel.personDetailsUiEntityLiveData.observe(this, Observer { handlePersonDetailsData(it) })
        fragmentViewModel.movieThumbnailsUiModelLiveData.observe(this, Observer { handleThumbnailsData(it) })
        fragmentViewModel.movieActorInClickedLiveEvent.observe(this, Observer { handleActorInMovieClickedEvent(it) })
    }

    private fun handlePersonDetailsData(personDetailsUiEntity: PersonDetailsUiEntity?) {


    }

    private fun handleThumbnailsData(uiModel: ScrollingThumbnailsViewUiModel?) {


    }

    private fun handleActorInMovieClickedEvent(movie: MovieActorInEntity?) {


    }

}
