package com.example.popularmovies.data.main.source.remote

import com.example.popularmovies.BuildConfig
import com.example.popularmovies.api.main.MoviesService
import com.example.popularmovies.data.details.entity.cast.PersonDetailsEntity
import com.example.popularmovies.data.details.entity.cast.ActorInMovieEntity
import com.example.popularmovies.data.details.entity.movie.MovieActorInEntity
import com.example.popularmovies.data.details.entity.movie.MovieDetailsEntity
import com.example.popularmovies.data.main.entity.MovieEntity
import com.example.popularmovies.data.main.source.remote.mapper.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRemoteDataSourceImpl @Inject constructor(

    responseMovieItemToEntityMapper: ResponseMovieItemToEntityMapper,

    responseCastItemToEntityMapper: ResponseCastItemToEntityMapper,

    responseCastDetailsToEntityMapper: ResponseCastDetailsToEntityMapper,

    movieDetailsResponseToEntityMapper: MovieDetailsResponseToEntityMapper,

    responseCastMovieItemToEntityMapper: ResponseCastMovieItemToEntityMapper,

    private val moviesService: MoviesService

) : MoviesRemoteDataSource(

    responseMovieItemToEntityMapper,

    movieDetailsResponseToEntityMapper,

    responseCastItemToEntityMapper,

    responseCastDetailsToEntityMapper,

    responseCastMovieItemToEntityMapper

) {

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

    override suspend fun getMovieDetails(movieId: Int): MovieDetailsEntity {

        // TODO Handle error fetching data
        val response = moviesService.getMovieDetailsAsync(
            endpoint = BuildConfig.ENDPOINT_MOVIES,
            movieId = movieId,
            key = BuildConfig.API_KEY,
            language = MOVIE_LANGUAGE
        ).await()

        return mapMovieDetailsResponseToModel(response)
    }

    override suspend fun getMovieCast(movieId: Int): List<ActorInMovieEntity> {

        // TODO Handle error fetching data
        val response = moviesService.getMovieCastAsync(
            endpoint = BuildConfig.ENDPOINT_MOVIES,
            movieId = movieId,
            key = BuildConfig.API_KEY,
            language = MOVIE_LANGUAGE
        ).await()

        return mapCastResponseItemsToModels(response)
    }

    override suspend fun getCastDetails(castId: Int): PersonDetailsEntity {

        // TODO Handle error fetching data
        val response = moviesService.getCastDetailsAsync(
            endpoint = BuildConfig.ENDPOINT_PERSON,
            castId = castId,
            key = BuildConfig.API_KEY,
            language = MOVIE_LANGUAGE
        ).await()

        return mapResponseCastDetailsToEntity(response)
    }

    override suspend fun getCastMovies(castId: Int): List<MovieActorInEntity> {

        // TODO Handle error fetching data
        val response = moviesService.getMoviesCreditsAsync(
            endpoint = BuildConfig.ENDPOINT_PERSON,
            castId = castId,
            key = BuildConfig.API_KEY,
            language = MOVIE_LANGUAGE
        ).await()

        return mapResponseCastMoviesToEntities(response)
    }

}
