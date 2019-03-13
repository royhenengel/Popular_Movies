package com.example.popularmovies.ui.main.viewmodels

import androidx.lifecycle.ViewModel
import com.example.popularmovies.data.main.MoviesRepository
import javax.inject.Inject

class MainMoviesFragmentViewModel @Inject constructor(

    private val moviesRepository: MoviesRepository

): ViewModel(){

    fun start(){

        getMovies()
    }

    private fun getMovies() {

        moviesRepository.getMovies()
    }

}
