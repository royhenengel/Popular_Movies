package com.example.popularmovies.ui.main.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popularmovies.data.main.MoviesRepository
import com.example.popularmovies.data.main.models.MovieModel
import javax.inject.Inject

class MainMoviesFragmentViewModel @Inject constructor(

    repository: MoviesRepository

) : ViewModel() {

    val moviesLiveData = repository.moviesPagedListLiveData
    val onMovieClickedLiveEvent = MutableLiveData<MovieModel?>()

    fun start() {}

    fun onMovieClicked(position: Int) {

        onMovieClickedLiveEvent.value = null
    }

}
