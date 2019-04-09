package com.example.popularmovies.ui.main.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.popularmovies.R
import com.example.popularmovies.data.main.models.MovieModel
import com.example.popularmovies.di.Injectable
import com.example.popularmovies.ui.main.viewmodels.MainMoviesFragmentViewModel
import javax.inject.Inject

class MainMoviesFragment : Fragment(), Injectable, MovieViewHolder.MovieClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var fragmentViewModel: MainMoviesFragmentViewModel

    private lateinit var moviesRv: RecyclerView
    private lateinit var moviesAdapter: MainMoviesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_main_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fragmentViewModel = ViewModelProviders.of(this,viewModelFactory).get(MainMoviesFragmentViewModel::class.java)
        fragmentViewModel.start()

        observe()
    }

    override fun onMovieClicked(position: Int) {

        fragmentViewModel.onMovieClicked(position)
    }

    private fun initViews(view: View) {

        moviesRv = view.findViewById<RecyclerView>(R.id.fragment_main_movies_rv).apply{

            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)

            moviesAdapter = MainMoviesAdapter(this@MainMoviesFragment)
            adapter = moviesAdapter
        }
    }

    private fun observe() {

        fragmentViewModel.moviesLiveData.observe(this, Observer { moviesAdapter.submitList(it) })
        fragmentViewModel.onMovieClickedLiveEvent.observe(this, Observer { handleMovieClickedEvent(it) })
    }

    private fun handleMovieClickedEvent(movieModel: MovieModel){

        val action = MainMoviesFragmentDirections.actionDestMainToDestMovieDetails(movieModel.id)
        findNavController().navigate(action)
    }

}
