package com.example.popularmovies.data.main.source.remote

import com.example.popularmovies.BuildConfig
import com.example.popularmovies.api.main.MoviesService
import com.example.popularmovies.data.main.models.MovieModel
import com.example.popularmovies.data.main.source.remote.mapper.ResponseMovieItemToModelMapper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRemoteDataSourceCoroutinesImpl @Inject constructor(

    private val moviesService: MoviesService,

    responseMovieItemToModelMapper: ResponseMovieItemToModelMapper

) : MoviesRemoteDataSource(responseMovieItemToModelMapper) {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, MovieModel>) {

        stateLiveData.postValue(STATE.LOADING)

        GlobalScope.launch {
            val response = moviesService.getMoviesAsync(
                endpoint = BuildConfig.ENDPOINT_MOVIES,
                category = CATEGORY.POPULAR.description,
                key = BuildConfig.API_KEY,
                language = MOVIE_LANGUAGE,
                page = FIRST_PAGE
            ).await()

            val mapResponseItemsToModels = mapResponseItemsToModels(response)
            callback.onResult(mapResponseItemsToModels, null, FIRST_PAGE + 1)
            stateLiveData.postValue(STATE.DONE)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieModel>) {

        stateLiveData.postValue(STATE.LOADING)

        GlobalScope.launch {
            val response = moviesService.getMoviesAsync(
                endpoint = BuildConfig.ENDPOINT_MOVIES,
                category = CATEGORY.POPULAR.description,
                key = BuildConfig.API_KEY,
                language = MOVIE_LANGUAGE,
                page = FIRST_PAGE
            ).await()

            val movieModelsList = mapResponseItemsToModels(response)
            callback.onResult(movieModelsList, keyAfter(params))
            stateLiveData.postValue(STATE.DONE)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieModel>) {

        stateLiveData.postValue(STATE.LOADING)

        GlobalScope.launch {
            val response = moviesService.getMoviesAsync(
                endpoint = BuildConfig.ENDPOINT_MOVIES,
                category = CATEGORY.POPULAR.description,
                key = BuildConfig.API_KEY,
                language = MOVIE_LANGUAGE,
                page = FIRST_PAGE
            ).await()

            val movieModelsList = mapResponseItemsToModels(response)
            callback.onResult(movieModelsList, keyBefore(params))
            stateLiveData.postValue(STATE.DONE)
        }
    }

}
