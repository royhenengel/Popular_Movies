package com.example.popularmovies.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.popularmovies.data.details.entity.cast.PersonDetailsEntity
import com.example.popularmovies.data.details.entity.cast.ActorInMovieEntity
import com.example.popularmovies.data.details.entity.movie.MovieActorInEntity
import com.example.popularmovies.data.details.entity.movie.MovieDetailsEntity
import com.example.popularmovies.data.main.entity.MovieEntity
import com.example.popularmovies.data.source.remote.MoviesRemoteDataSource
import com.example.popularmovies.data.source.remote.MoviesRemoteDataSourceFactory
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

    suspend fun getMovieCast(movieId: Int): List<ActorInMovieEntity>{

        return moviesRemoteDataSource.getMovieCast(movieId)
    }

    suspend fun getCastDetails(castId: Int): PersonDetailsEntity {

        return moviesRemoteDataSource.getCastDetails(castId)
    }

    suspend fun getCastMovies(castId: Int): List<MovieActorInEntity> {

        return moviesRemoteDataSource.getCastMovies(castId)
    }

}