package com.example.popularmovies.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.popularmovies.data.details.entity.cast.ActorInMovieEntity
import com.example.popularmovies.data.details.entity.cast.PersonDetailsEntity
import com.example.popularmovies.data.details.entity.movie.MovieActorInEntity
import com.example.popularmovies.data.details.entity.movie.MovieDetailsEntity
import com.example.popularmovies.data.main.entity.MovieEntity
import com.example.popularmovies.data.main.entity.MovieGenreEntity
import com.example.popularmovies.data.source.remote.MoviesRemoteDataSource
import com.example.popularmovies.data.source.remote.MoviesPagedKeyDataSource
import com.example.popularmovies.data.source.remote.MoviesRemoteDataSourceFactory
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
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
                .setPageSize(MoviesPagedKeyDataSource.PAGE_SIZE)
                .build()

        LivePagedListBuilder(moviesRemoteDataSourceFactory, config).build()
    }

    var dataSourceState: LiveData<MoviesPagedKeyDataSource.STATE> =
            Transformations.switchMap(moviesRemoteDataSourceFactory.dataSource) { dataSource -> dataSource.stateLiveData}

    private val moviesPagedKeyedDataSource = moviesRemoteDataSourceFactory.dataSource

    fun getMovieDetails(movieId: Int): Single<MovieDetailsEntity> {

        return moviesRemoteDataSource.getMovieDetails(movieId)
    }

    fun getMovieCast(movieId: Int): Single<List<ActorInMovieEntity>> {

        return moviesRemoteDataSource.getMovieCast(movieId)
    }

    fun getPersonDetails(castId: Int): Single<PersonDetailsEntity> {

        return moviesRemoteDataSource.getCastDetails(castId)
                .subscribeOn(Schedulers.io())
    }

    fun getPersonMovies(castId: Int): Single<List<MovieActorInEntity>> {

        return moviesRemoteDataSource.getCastMovies(castId)
                .subscribeOn(Schedulers.io())
    }

    fun getMovieGenres(): List<MovieGenreEntity>{

        return moviesRemoteDataSource.movieGenresList
    }

    fun restartMoviesDataSource() {

        moviesPagedKeyedDataSource.value?.invalidate()
    }

}