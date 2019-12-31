package com.example.popularmovies.ui.view.main.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.popularmovies.data.entity.MovieEntity
import com.example.popularmovies.ui.view.main.model.MovieUiModel
import com.example.popularmovies.domain.repository.MoviesRepository
import com.example.popularmovies.ui.viewmodel.SingleLiveEvent
import timber.log.Timber
import javax.inject.Inject

class MainMoviesFragmentViewModel @Inject constructor(

        private val repository: MoviesRepository

) : ViewModel() {

    val moviesLiveData = repository.moviesPagedListLiveData()

    val stateLiveData = MediatorLiveData<STATE>()

    val onMovieClickedLiveEvent = SingleLiveEvent<MovieEntity>()

    val userErrorMessage = MESSAGE_USER_ERROR

    fun start(){
        stateLiveData.value = STATE.LOADING

        stateLiveData.removeSource(repository.dataSourceState())

        stateLiveData.addSource(repository.dataSourceState()) { dataSourceState: MoviesRemoteDataSource.STATE ->
            when(dataSourceState){
                MoviesRemoteDataSource.STATE.LOADED -> handleDataSourceStateLoaded()

                MoviesRemoteDataSource.STATE.LOADING -> handleDataSourceStateLoading()

                MoviesRemoteDataSource.STATE.ERROR -> handleDataSourceStateError()
            }
        }
    }

    private fun handleDataSourceStateLoaded() {
        stateLiveData.value = STATE.LOADED
    }

    private fun handleDataSourceStateLoading() {}

    private fun handleDataSourceStateError() {

        Timber.e(MESSAGE_INTERNAL_ERROR)
        stateLiveData.value =
            STATE.ERROR
    }

    fun onMovieClicked(movieUiModel: MovieUiModel) {

        val entity = getEntityFromUiEntity(movieUiModel)
        onMovieClickedLiveEvent.value = entity
    }

    fun onTryAgainClicked() {
        stateLiveData.value = STATE.LOADING

        repository.restartMoviesDataSource()
    }

    private fun getEntityFromUiEntity(movieUiModel: MovieUiModel): MovieEntity? {
        val pagedList = moviesLiveData.value

        for (entity in pagedList!!) {
            if (entity.id == movieUiModel.id)
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
