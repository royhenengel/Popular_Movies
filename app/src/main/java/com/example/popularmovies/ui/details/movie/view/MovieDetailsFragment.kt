package com.example.popularmovies.ui.details.movie.view

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.popularmovies.BuildConfig
import com.example.popularmovies.R
import com.example.popularmovies.data.details.model.MovieDetailsModel
import com.example.popularmovies.di.Injectable
import com.example.popularmovies.ui.details.movie.viewmodel.MovieDetailsFragmentViewModel
import com.example.popularmovies.util.dateAsString
import kotlinx.android.synthetic.main.fragment_movie_details.*
import javax.inject.Inject

class MovieDetailsFragment : Fragment(), Injectable {

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

    private fun observe() {

        fragmentViewModel.movieDetailsModelLiveData.observe(this, Observer { handleMovieData(it) })
    }

    private fun handleMovieData(movieModel: MovieDetailsModel) {

        movieModel.let {
            titleTv.text = it.title
            yearTv.text = dateAsString(it.releaseDate, PATTERN_YEAR)
            overviewTv.text = it.overview
            scoreTv.text = it.score.toString()
            lengthTv.text = String.format(PATTERN_MOVIE_LENGTH, it.length)

            if (it.thumbnailPath != null) {
                Glide.with(context!!)
                    .load("${BuildConfig.MOVIES_IMAGE_BASE_URL}${it.thumbnailPath}")
                    .listener(object : RequestListener<Drawable>{

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
            else{
                Glide.with(context!!)
                    .clear(imageBackgroundIv)
            }
        }
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

        private const val PATTERN_YEAR = "yyyy"
        private const val PATTERN_MOVIE_LENGTH = "%d min"
        private const val MESSAGE_ARGS_NOT_RECEIVED = "Movie model wan't received, can't start fragment"
    }
}
