package com.example.popularmovies.ui.details.movie.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.popularmovies.R
import com.example.popularmovies.ui.details.movie.viewmodel.MovieDetailsFragmentViewModel

class MovieDetailsFragment : Fragment() {

    private lateinit var viewModel: MovieDetailsFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MovieDetailsFragmentViewModel::class.java)
    }

}
