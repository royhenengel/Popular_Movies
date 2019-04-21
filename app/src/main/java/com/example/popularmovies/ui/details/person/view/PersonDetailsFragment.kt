package com.example.popularmovies.ui.details.person.view

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
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
import com.example.popularmovies.data.details.entity.movie.MovieActorInEntity
import com.example.popularmovies.di.Injectable
import com.example.popularmovies.ui.common.scrollingthumbnail.entity.ScrollingThumbnailClickListener
import com.example.popularmovies.ui.common.scrollingthumbnail.viewmodel.ScrollingThumbnailsViewUiModel
import com.example.popularmovies.ui.details.person.entity.PersonDetailsUiEntity
import com.example.popularmovies.ui.details.person.viewmodel.PersonDetailsFragmentViewModel
import kotlinx.android.synthetic.main.fragment_person_details.*
import javax.inject.Inject

class PersonDetailsFragment : Fragment(), Injectable, ScrollingThumbnailClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var fragmentViewModel: PersonDetailsFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_person_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.menu_person_details, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            R.id.action_home -> {
                fragmentViewModel.onToolbarHomeClicked()
                true
            }

            R.id.action_open_in_browser -> {
                fragmentViewModel.onOpenInBrowserClicked()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fragmentViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(PersonDetailsFragmentViewModel::class.java)

        arguments?.let {
            fragmentViewModel.start(PersonDetailsFragmentArgs.fromBundle(it).personId)
        }

        observe()
    }

    override fun onThumbnailClicked(position: Int) {

        fragmentViewModel.onThumbnailClicked(position)
    }

    private fun initViews() {

        personDetailsActionTryAgainBtn.setOnClickListener { fragmentViewModel.onTryAgainBtnClicked() }
    }

    private fun observe() {

        fragmentViewModel.personDetailsUiEntityLiveData.observe(this, Observer { handlePersonDetailsData(it) })
        fragmentViewModel.movieThumbnailsUiModelLiveData.observe(this, Observer { handleThumbnailsData(it) })
        fragmentViewModel.stateLiveData.observe(this, Observer { handleStateData(it) })
        fragmentViewModel.movieActorInClickedLiveEvent.observe(this, Observer { handleActorInMovieClickedEvent(it) })
        fragmentViewModel.openInBrowserLiveEvent.observe(this, Observer { handleOpenInBrowserEvent(it) })
        fragmentViewModel.actionHomeLiveEvent.observe(this, Observer { handleActionHomeEvent() })
        fragmentViewModel.onErrorLiveEvent.observe(this, Observer { handleOnErrorEvent(it) })
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
                        fragmentViewModel.onLoadPersonImageError(e)
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

    private fun handleStateData(state: PersonDetailsFragmentViewModel.STATE) {

        when(state){

            PersonDetailsFragmentViewModel.STATE.LOADING -> showLoading()

            PersonDetailsFragmentViewModel.STATE.ERROR -> showError()

            PersonDetailsFragmentViewModel.STATE.DONE -> showLayout()
        }
    }

    private fun handleThumbnailsData(uiModel: ScrollingThumbnailsViewUiModel) {

        personDetailsMoviesScrollingThumbnailsV.setClickListener(this)
        personDetailsMoviesScrollingThumbnailsV.setUiModel(uiModel, this)
    }

    private fun handleActorInMovieClickedEvent(movieActorInEntity: MovieActorInEntity) {

        val action = PersonDetailsFragmentDirections.actionDestPersonDetailsToDestMovieDetails(
            movieActorInEntity.id,
            true
        )
        findNavController().navigate(action)
    }

    private fun handleOnErrorEvent(message: String) {

        personDetailsErrorTv.text = message

        personDetailsLoaderPb.visibility = View.GONE
        personDetailsErrorViewsGroup.visibility = View.VISIBLE
    }

    private fun handleActionHomeEvent() {

        findNavController().popBackStack(R.id.dest_main, false)
    }

    private fun handleOpenInBrowserEvent(url: String) {

        val intent = Intent(Intent.ACTION_VIEW).also {
            it.data = Uri.parse(url)
        }
        startActivity(intent)
    }

    /**
     * Show fragment layout only if both person details has be received and background image has been
     * downloaded and set
     */
    private fun showLayout() {

        personDetailsLoaderPb.visibility = View.GONE
        personDetailsErrorViewsGroup.visibility = View.GONE
        personDetailsLayoutViewsGroup.visibility = View.VISIBLE
    }

    private fun showLoading(){

        personDetailsErrorViewsGroup.visibility = View.GONE
        personDetailsLayoutViewsGroup.visibility = View.GONE
        personDetailsLoaderPb.visibility = View.VISIBLE
    }

    private fun showError(){

        personDetailsLayoutViewsGroup.visibility = View.GONE
        personDetailsLoaderPb.visibility = View.GONE
        personDetailsErrorViewsGroup.visibility = View.VISIBLE
    }

}
