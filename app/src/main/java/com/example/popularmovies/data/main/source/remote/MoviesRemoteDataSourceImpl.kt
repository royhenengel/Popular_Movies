package com.example.popularmovies.data.main.source.remote

import com.example.popularmovies.BuildConfig
import com.example.popularmovies.api.main.MoviesService
import com.example.popularmovies.data.details.entity.cast.CastDetailsEntity
import com.example.popularmovies.data.details.entity.cast.CastEntity
import com.example.popularmovies.data.details.entity.movie.MovieDetailsEntity
import com.example.popularmovies.data.main.entity.MovieEntity
import com.example.popularmovies.data.main.source.remote.mapper.MovieDetailsResponseToEntityMapper
import com.example.popularmovies.data.main.source.remote.mapper.ResponseCastItemToEntityMapper
import com.example.popularmovies.data.main.source.remote.mapper.ResponseMovieItemToEntityMapper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRemoteDataSourceImpl @Inject constructor(

        responseMovieItemToEntityMapper: ResponseMovieItemToEntityMapper,

        responseCastItemToEntityMapper: ResponseCastItemToEntityMapper,

        movieDetailsResponseToEntityMapper: MovieDetailsResponseToEntityMapper,

        private val moviesService: MoviesService

) : MoviesRemoteDataSource(responseMovieItemToEntityMapper, movieDetailsResponseToEntityMapper, responseCastItemToEntityMapper) {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, MovieEntity>) {

        stateLiveData.postValue(STATE.LOADING)
        // TODO Handle error fetching data
        GlobalScope.launch {
            val response = moviesService.getMoviesAsync(
                endpoint = BuildConfig.ENDPOINT_MOVIES,
                category = CATEGORY.POPULAR.description,
                key = BuildConfig.API_KEY,
                language = MOVIE_LANGUAGE,
                page = FIRST_PAGE
            ).await()

            val mapResponseItemsToModels = mapPopularMovieResponseItemsToModels(response)
            callback.onResult(mapResponseItemsToModels, null, FIRST_PAGE + 1)
            stateLiveData.postValue(STATE.DONE)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieEntity>) {

        stateLiveData.postValue(STATE.LOADING)
        // TODO Handle error fetching data
        GlobalScope.launch {
            val response = moviesService.getMoviesAsync(
                endpoint = BuildConfig.ENDPOINT_MOVIES,
                category = CATEGORY.POPULAR.description,
                key = BuildConfig.API_KEY,
                language = MOVIE_LANGUAGE,
                page = FIRST_PAGE
            ).await()

            val movieModelsList = mapPopularMovieResponseItemsToModels(response)
            callback.onResult(movieModelsList, keyAfter(params))
            stateLiveData.postValue(STATE.DONE)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieEntity>) {

        stateLiveData.postValue(STATE.LOADING)
        // TODO Handle error fetching data
        GlobalScope.launch {
            val response = moviesService.getMoviesAsync(
                endpoint = BuildConfig.ENDPOINT_MOVIES,
                category = CATEGORY.POPULAR.description,
                key = BuildConfig.API_KEY,
                language = MOVIE_LANGUAGE,
                page = FIRST_PAGE
            ).await()

            val movieModelsList = mapPopularMovieResponseItemsToModels(response)
            callback.onResult(movieModelsList, keyBefore(params))
            stateLiveData.postValue(STATE.DONE)
        }
    }

    override suspend fun getMovieDetails(movieId: Int) : MovieDetailsEntity {

        // TODO Handle error fetching data
        val response = moviesService.getMovieDetailsAsync(
                endpoint = BuildConfig.ENDPOINT_MOVIES,
                movieId = movieId,
                key = BuildConfig.API_KEY,
                language = MOVIE_LANGUAGE
        ).await()

        return mapMovieDetailsResponseToModel(response)
    }

    override suspend fun getMovieCast(movieId: Int): List<CastEntity> {

        // TODO Handle error fetching data
        val response = moviesService.getMovieCastAsync(
                endpoint = BuildConfig.ENDPOINT_MOVIES,
                movieId = movieId,
                key = BuildConfig.API_KEY,
                language = MOVIE_LANGUAGE
        ).await()

        return mapCastResponseItemsToModels(response)
    }

    override suspend fun getCastDetails(castId: Int): CastDetailsEntity {

        // TODO Handle error fetching data
        val response = moviesService.getCastDetailsAsync(
            endpoint = BuildConfig.ENDPOINT_PERSON,
            castId = castId,
            key = BuildConfig.API_KEY,
            language = MOVIE_LANGUAGE
        ).await()

        return CastDetailsEntity(castId)
    }

}
