package com.example.popularmovies.data.source.remote

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.popularmovies.api.MoviesService
import com.example.popularmovies.data.main.entity.MovieEntity
import com.example.popularmovies.data.source.remote.mapper.ResponseMovieGenresItemToEntityMapper
import com.example.popularmovies.data.source.remote.mapper.ResponseMovieItemToEntityMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRemoteDataSourceFactory @Inject constructor(

    private val responseMovieItemToEntityMapper: ResponseMovieItemToEntityMapper,

    private val responseMovieGenresItemToEntityMapper: ResponseMovieGenresItemToEntityMapper,

    private val moviesService: MoviesService

) : DataSource.Factory<Int, MovieEntity>() {

    val dataSource = MutableLiveData<MoviesRemoteDataSource>()

    override fun create(): DataSource<Int, MovieEntity> {

        val moviesRemoteDataSourceImpl = MoviesRemoteDataSourceImpl(
            moviesService, responseMovieGenresItemToEntityMapper,
            responseMovieItemToEntityMapper
        )

        dataSource.postValue(moviesRemoteDataSourceImpl)

        return moviesRemoteDataSourceImpl
    }

}