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
    }

}
