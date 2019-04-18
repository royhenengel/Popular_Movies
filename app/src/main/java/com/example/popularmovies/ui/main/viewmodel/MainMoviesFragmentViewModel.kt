package com.example.popularmovies.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import com.example.popularmovies.data.MoviesRepository
import com.example.popularmovies.data.main.entity.MovieEntity
import com.example.popularmovies.ui.main.entity.MovieUiEntity
import com.example.popularmovies.viewmodel.SingleLiveEvent
import javax.inject.Inject

class MainMoviesFragmentViewModel @Inject constructor(

    repository: MoviesRepository

) : ViewModel() {

    val moviesLiveData = repository.moviesPagedListLiveData
    val onMovieClickedLiveEvent = SingleLiveEvent<MovieEntity>()

    fun onMovieClicked(movieUiEntity: MovieUiEntity) {

        val entity = getEntityFromUiEntity(movieUiEntity)
        onMovieClickedLiveEvent.value = entity
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

        private const val MASSAGE_MOVIE_NOT_FOUND = "Movie clicked not found"
    }

}
