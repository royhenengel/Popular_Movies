package com.example.popularmovies.ui.main.viewmodels

import androidx.lifecycle.ViewModel
import com.example.popularmovies.data.main.MoviesRepository
import javax.inject.Inject

class MainMoviesFragmentViewModel @Inject constructor(

    repository: MoviesRepository

) : ViewModel() {

    val moviesLiveData = repository.moviesPagedListLiveData

    fun start() {}

}
