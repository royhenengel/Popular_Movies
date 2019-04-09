package com.example.popularmovies.ui.details.movie.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popularmovies.data.details.model.MovieDetailsModel
import com.example.popularmovies.data.main.MoviesRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class MovieDetailsFragmentViewModel @Inject constructor(

        private val repository: MoviesRepository

) : ViewModel() {

    private var getMovieDetailsJob: Job? = null

    val movieDetailsModelLiveData = MutableLiveData<MovieDetailsModel>()

    override fun onCleared() {
        super.onCleared()

        getMovieDetailsJob?.cancel()
    }

    fun start(movieId: Int) {

        getMovieDetailsJob = CoroutineScope(Dispatchers.Default).launch {

            val movieDetails = repository.getMovieDetails(movieId)
            withContext(Dispatchers.Main){
                movieDetailsModelLiveData.value = movieDetails
            }
        }
    }

}
