package com.example.popularmovies.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.popularmovies.data.details.model.cast.CastEntity
import com.example.popularmovies.data.details.model.movie.MovieDetailsEntity
import com.example.popularmovies.data.main.models.MovieEntity
import com.example.popularmovies.data.main.source.remote.MoviesRemoteDataSource
import com.example.popularmovies.data.main.source.remote.MoviesRemoteDataSourceFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(

        moviesRemoteDataSourceFactory: MoviesRemoteDataSourceFactory,

        private val moviesRemoteDataSource: MoviesRemoteDataSource

) {

    val moviesPagedListLiveData: LiveData<PagedList<MovieEntity>> by lazy {

        val config: PagedList.Config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(MoviesRemoteDataSource.PAGE_SIZE)
                .build()

        LivePagedListBuilder(moviesRemoteDataSourceFactory, config).build()
    }

    suspend fun getMovieDetails(movieId: Int): MovieDetailsEntity {

        return moviesRemoteDataSource.getMovieDetails(movieId)
    }

    suspend fun getMovieCast(movieId: Int): List<CastEntity>{

        return moviesRemoteDataSource.getMovieCast(movieId)
    }

}