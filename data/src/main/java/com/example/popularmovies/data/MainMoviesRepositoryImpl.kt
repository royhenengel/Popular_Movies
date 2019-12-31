package com.example.popularmovies.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.popularmovies.domain.repository.MoviesRepository
import com.example.popularmovies.ui.model.*
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepositoryImpl @Inject constructor(

    private val moviesRemoteDataSourceFactory: MoviesRemoteDataSourceFactory,

    private val detailsRemoteDataSource: DetailsRemoteDataSource

): MoviesRepository {

    private val moviesRemoteDataSource = moviesRemoteDataSourceFactory.dataSource

    override fun moviesPagedListLiveData(): LiveData<PagedList<Movie>> {
        val config: PagedList.Config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(MoviesRemoteDataSource.PAGE_SIZE)
            .build()

        return LivePagedListBuilder(moviesRemoteDataSourceFactory, config).build()
    }

    override fun dataSourceState(): LiveData<MoviesRemoteDataSource.STATE> =
        Transformations.switchMap(moviesRemoteDataSourceFactory.dataSource) { dataSource -> dataSource.stateLiveData}


    override fun getMovieDetails(movieId: Int): Single<MovieDetails> {
        return detailsRemoteDataSource.getMovieDetails(movieId)
    }

    override fun getMovieCast(movieId: Int): Single<List<ActorInMovie>> {
        return detailsRemoteDataSource.getMovieCast(movieId)
    }

    override fun getPersonDetails(castId: Int): Single<PersonDetails> {
        return detailsRemoteDataSource.getCastDetails(castId)
                .subscribeOn(Schedulers.io())
    }

    override fun getPersonMovies(castId: Int): Single<List<MovieActorIn>> {
        return detailsRemoteDataSource.getCastMovies(castId)
                .subscribeOn(Schedulers.io())
    }

    override fun restartMoviesDataSource() {
        moviesRemoteDataSource.value.invalidate()
    }

}