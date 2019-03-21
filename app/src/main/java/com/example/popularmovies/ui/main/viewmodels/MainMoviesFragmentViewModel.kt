package com.example.popularmovies.ui.main.viewmodels

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popularmovies.data.main.MoviesRepository
import com.example.popularmovies.data.main.models.MovieModel
import kotlinx.coroutines.*
import javax.inject.Inject

class MainMoviesFragmentViewModel @Inject constructor(

    private val moviesRepository: MoviesRepository

) : ViewModel() {

    var popularMoviesLiveData: MutableLiveData<ArrayList<MovieModel>> = MutableLiveData()

    private var getPopularMoviesJob : Job? = null

    override fun onCleared() {
        super.onCleared()

        getPopularMoviesJob?.cancel()
    }

    fun start() {

        getMovies()
    }

    private fun getMovies() {

        getPopularMoviesJob = CoroutineScope(Dispatchers.IO).launch {

            val movies = moviesRepository.getMovies()
            withContext(Dispatchers.Main){
                popularMoviesLiveData.value = movies
            }
        }
    }

}
