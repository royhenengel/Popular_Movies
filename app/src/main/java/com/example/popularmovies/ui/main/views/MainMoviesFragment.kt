package com.example.popularmovies.ui.main.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.popularmovies.R
import com.example.popularmovies.ui.main.viewmodels.MainMoviesFragmentViewModel

class MainMoviesFragment : Fragment() {

    companion object {
        fun newInstance() = MainMoviesFragment()
    }

    private lateinit var viewModel: MainMoviesFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_main_movies, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MainMoviesFragmentViewModel::class.java)
    }

}
