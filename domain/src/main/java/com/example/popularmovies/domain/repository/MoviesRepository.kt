package com.example.popularmovies.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.popularmovies.ui.model.*
import io.reactivex.Single

interface MoviesRepository {

    fun moviesPagedListLiveData(): LiveData<PagedList<Movie>>

    fun dataSourceState(): LiveData<MoviesRemoteDataSource.STATE>

    fun getMovieDetails(movieId: Int): Single<MovieDetails>

    fun getMovieCast(movieId: Int): Single<List<ActorInMovie>>

    fun getPersonDetails(castId: Int): Single<PersonDetails>

    fun getPersonMovies(castId: Int): Single<List<MovieActorIn>>

    fun restartMoviesDataSource()
}