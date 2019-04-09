package com.example.popularmovies.ui.main.viewmodels

import androidx.lifecycle.ViewModel
import com.example.popularmovies.data.main.MoviesRepository
import com.example.popularmovies.data.main.models.MovieModel
import com.example.popularmovies.viewmodel.SingleLiveEvent
import javax.inject.Inject

class MainMoviesFragmentViewModel @Inject constructor(

    repository: MoviesRepository

) : ViewModel() {

    val moviesLiveData = repository.moviesPagedListLiveData
    val onMovieClickedLiveEvent = SingleLiveEvent<MovieModel>()

    fun start() {}

    fun onMovieClicked(position: Int) {

        val movieModel = moviesLiveData.value?.get(position) ?: throw ClassNotFoundException(MASSAGE_MOVIE_NOT_FOUND)
        onMovieClickedLiveEvent.value = movieModel
    }

    companion object {

        private const val MASSAGE_MOVIE_NOT_FOUND = "Movie clicked not found"
    }

}
