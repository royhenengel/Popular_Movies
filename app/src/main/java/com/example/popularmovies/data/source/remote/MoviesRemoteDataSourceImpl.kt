package com.example.popularmovies.data.source.remote

import android.annotation.SuppressLint
import com.example.popularmovies.BuildConfig
import com.example.popularmovies.api.MoviesService
import com.example.popularmovies.data.main.entity.MovieEntity
import com.example.popularmovies.data.source.remote.mapper.*
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRemoteDataSourceImpl @Inject constructor(

        private val moviesService: MoviesService,

        responseMovieItemToEntityMapper: ResponseMovieItemToEntityMapper

) : MoviesRemoteDataSource(responseMovieItemToEntityMapper) {

    @SuppressLint("CheckResult")
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, MovieEntity>) {

        stateLiveData.postValue(STATE.LOADING)
        moviesService.getMoviesAsync(
                endpoint = BuildConfig.ENDPOINT_MOVIES,
                category = CATEGORY.POPULAR.description,
                key = BuildConfig.API_KEY,
                language = MOVIE_LANGUAGE,
                page = FIRST_PAGE
        )
                .subscribeOn(Schedulers.io())
                .map { response -> mapPopularMovieResponseItemsToModels(response) }
                .subscribe({ movieModelList ->
                    callback.onResult(movieModelList, null, FIRST_PAGE + 1)
                    stateLiveData.postValue(STATE.LOADED)
                }, { t ->
                    Timber.e(t)
                    stateLiveData.postValue(STATE.ERROR)
                })

    }

    @SuppressLint("CheckResult")
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieEntity>) {

        stateLiveData.postValue(STATE.LOADING)
        moviesService.getMoviesAsync(
                endpoint = BuildConfig.ENDPOINT_MOVIES,
                category = CATEGORY.POPULAR.description,
                key = BuildConfig.API_KEY,
                language = MOVIE_LANGUAGE,
                page = FIRST_PAGE
        )
                .subscribeOn(Schedulers.io())
                .map { response -> mapPopularMovieResponseItemsToModels(response) }
                .subscribe({ movieModelList ->
                    callback.onResult(movieModelList, keyAfter(params))
                    stateLiveData.postValue(STATE.LOADED)
                }, { t ->
                    Timber.e(t)
                    stateLiveData.postValue(STATE.ERROR)
                })
    }

    @SuppressLint("CheckResult")
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieEntity>) {

        stateLiveData.postValue(STATE.LOADING)
        moviesService.getMoviesAsync(
                endpoint = BuildConfig.ENDPOINT_MOVIES,
                category = CATEGORY.POPULAR.description,
                key = BuildConfig.API_KEY,
                language = MOVIE_LANGUAGE,
                page = FIRST_PAGE
        )
                .subscribeOn(Schedulers.io())
                .map { response -> mapPopularMovieResponseItemsToModels(response) }
                .subscribe({ movieModelList ->
                    callback.onResult(movieModelList, keyBefore(params))
                    stateLiveData.postValue(STATE.LOADED)
                }, { t ->
                    Timber.e(t)
                    stateLiveData.postValue(STATE.ERROR)
                })
    }

}
