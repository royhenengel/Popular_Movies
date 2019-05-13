package com.example.popularmovies.ui.main.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.popularmovies.data.MoviesRepository
import com.example.popularmovies.data.main.entity.MovieEntity
import com.example.popularmovies.data.source.remote.MoviesRemoteDataSource
import com.example.popularmovies.ui.main.entity.MovieUiEntity
import com.example.popularmovies.viewmodel.SingleLiveEvent
import timber.log.Timber
import javax.inject.Inject

class MainMoviesFragmentViewModel @Inject constructor(

        private val repository: MoviesRepository

) : ViewModel() {

    val moviesLiveData = repository.moviesPagedListLiveData

    val stateLiveData = MediatorLiveData<STATE>()

    val onMovieClickedLiveEvent = SingleLiveEvent<MovieEntity>()
    val onActionFilterClickedLiveEvent = SingleLiveEvent<Unit?>()

    val userErrorMessage = MESSAGE_USER_ERROR

    fun start(){

        stateLiveData.value = STATE.LOADING
        stateLiveData.removeSource(repository.dataSourceState)
        stateLiveData.addSource(repository.dataSourceState) { dataSourceState: MoviesRemoteDataSource.STATE ->

            when(dataSourceState){

                MoviesRemoteDataSource.STATE.LOADED -> handleDataSourceStateLoaded()

                MoviesRemoteDataSource.STATE.LOADING -> handleDataSourceStateLoading()

                MoviesRemoteDataSource.STATE.ERROR -> handleDataSourceStateError()
            }
        }
    }

    fun onActionFilterBtnClicked() {

        onActionFilterClickedLiveEvent.call()
    }

    private fun handleDataSourceStateLoaded() {

        stateLiveData.value = STATE.LOADED
    }

    private fun handleDataSourceStateLoading() {


    }

    private fun handleDataSourceStateError() {

        Timber.e(MESSAGE_INTERNAL_ERROR)
        stateLiveData.value = STATE.ERROR
    }

    fun onMovieClicked(movieUiEntity: MovieUiEntity) {

        val entity = getEntityFromUiEntity(movieUiEntity)
        onMovieClickedLiveEvent.value = entity
    }

    fun onTryAgainClicked() {

        stateLiveData.value = STATE.LOADING
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
