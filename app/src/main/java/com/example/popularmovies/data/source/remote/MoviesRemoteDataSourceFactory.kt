package com.example.popularmovies.data.source.remote

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.popularmovies.api.MoviesService
import com.example.popularmovies.data.main.entity.MovieEntity
import com.example.popularmovies.data.source.remote.mapper.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRemoteDataSourceFactory @Inject constructor(

        private val responseMovieItemToEntityMapper: ResponseMovieItemToEntityMapper,

        private val responseActorInMovieItemToEntityMapper: ResponseActorInMovieItemToEntityMapper,

        private val responsePersonDetailsToEntityMapper: ResponsePersonDetailsToEntityMapper,

        private val responseMovieDetailsToEntityMapper: ResponseMovieDetailsToEntityMapper,

        private val responseMovieActorInItemToEntityMapper: ResponseMovieActorInItemToEntityMapper,

        private val moviesService: MoviesService

) : DataSource.Factory<Int, MovieEntity>() {

    val dataSource = MutableLiveData<MoviesRemoteDataSource>()

    var dataSourceState = MutableLiveData<MoviesRemoteDataSource.STATE>()

    override fun create(): DataSource<Int, MovieEntity> {

        val moviesRemoteDataSourceImpl = MoviesRemoteDataSourceImpl(responseMovieItemToEntityMapper, responseActorInMovieItemToEntityMapper,
                responsePersonDetailsToEntityMapper, responseMovieDetailsToEntityMapper,
                responseMovieActorInItemToEntityMapper, moviesService)

        dataSource.postValue(moviesRemoteDataSourceImpl)
        dataSourceState = moviesRemoteDataSourceImpl.stateLiveData

        return moviesRemoteDataSourceImpl
    }

}