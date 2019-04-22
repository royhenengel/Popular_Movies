package com.example.popularmovies.ui.main.viewmodel

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.popularmovies.data.MoviesRepository
import com.example.popularmovies.data.main.entity.MovieEntity
import com.example.popularmovies.data.source.remote.MoviesRemoteDataSource
import com.example.popularmovies.ui.main.entity.MovieUiEntity
import com.example.popularmovies.viewmodel.SingleLiveEvent
import javax.inject.Inject

class MainMoviesFragmentViewModel @Inject constructor(

        private val repository: MoviesRepository

) : ViewModel() {

    val moviesLiveData = repository.moviesPagedListLiveData
    val stateLiveData = Transformations.map(repository.dataSourceState)
    { state: MoviesRemoteDataSource.STATE ->
        when (state) {
            MoviesRemoteDataSource.STATE.LOADED -> STATE.LOADED
            MoviesRemoteDataSource.STATE.LOADING -> STATE.LOADING
            MoviesRemoteDataSource.STATE.ERROR -> STATE.ERROR
        }
    }

    val onMovieClickedLiveEvent = SingleLiveEvent<MovieEntity>()

    val userErrorMessage = MESSAGE_USER_ERROR

    fun onMovieClicked(movieUiEntity: MovieUiEntity) {

        val entity = getEntityFromUiEntity(movieUiEntity)
        onMovieClickedLiveEvent.value = entity
    }

    fun onTryAgainClicked() {

        repository.restartMoviesDataSource()
    }

    private fun getEntityFromUiEntity(movieUiEntity: MovieUiEntity): MovieEntity? {

        val pagedList = moviesLiveData.value
        for (entity in pagedList!!) {
            if (entity.id == movieUiEntity.id)
                return entity
        }

        return null
    }

    companion object {

        private const val MESSAGE_INTERNAL_ERROR = "Error getting data for main movies fragment"
        private const val MESSAGE_USER_ERROR = "Error loading data\nPlease check your internet connection\nand try again"
    }

    enum class STATE {
        LOADING, ERROR, LOADED
    }

}
