package com.example.popularmovies.data.source.remote

import com.example.popularmovies.BuildConfig
import com.example.popularmovies.api.MoviesService
import com.example.popularmovies.data.details.entity.cast.ActorInMovieEntity
import com.example.popularmovies.data.details.entity.cast.PersonDetailsEntity
import com.example.popularmovies.data.details.entity.movie.MovieActorInEntity
import com.example.popularmovies.data.details.entity.movie.MovieDetailsEntity
import com.example.popularmovies.data.source.remote.mapper.ResponseActorInMovieItemToEntityMapper
import com.example.popularmovies.data.source.remote.mapper.ResponseMovieActorInItemToEntityMapper
import com.example.popularmovies.data.source.remote.mapper.ResponseMovieDetailsToEntityMapper
import com.example.popularmovies.data.source.remote.mapper.ResponsePersonDetailsToEntityMapper
import io.reactivex.Single
import javax.inject.Inject

class DetailsRemoteDataSourceImpl @Inject constructor(

        responseMovieDetailsToEntityMapper: ResponseMovieDetailsToEntityMapper,

        responseActorInMovieItemToEntityMapper: ResponseActorInMovieItemToEntityMapper,

        responsePersonDetailsToEntityMapper: ResponsePersonDetailsToEntityMapper,

        responseMovieActorInItemToEntityMapper: ResponseMovieActorInItemToEntityMapper,

        private val moviesService: MoviesService

) : DetailsRemoteDataSource(responseMovieDetailsToEntityMapper, responseActorInMovieItemToEntityMapper,
        responsePersonDetailsToEntityMapper, responseMovieActorInItemToEntityMapper) {

    override suspend fun getMovieDetails(movieId: Int): MovieDetailsEntity {

        // TODO Handle error fetching data
        val response = moviesService.getMovieDetailsAsync(
                endpoint = BuildConfig.ENDPOINT_MOVIES,
                movieId = movieId,
                key = BuildConfig.API_KEY,
                language = MoviesRemoteDataSource.MOVIE_LANGUAGE
        ).await()

        return mapMovieDetailsResponseToModel(response)
    }

    override suspend fun getMovieCast(movieId: Int): List<ActorInMovieEntity> {

        // TODO Handle error fetching data
        val response = moviesService.getMovieCastAsync(
                endpoint = BuildConfig.ENDPOINT_MOVIES,
                movieId = movieId,
                key = BuildConfig.API_KEY,
                language = MoviesRemoteDataSource.MOVIE_LANGUAGE
        ).await()

        return mapActorsInMovieResponseItemsToEntities(response)
    }

    override fun getCastDetails(castId: Int): Single<PersonDetailsEntity> {

        return moviesService.getCastDetailsAsync(
                endpoint = BuildConfig.ENDPOINT_PERSON,
                castId = castId,
                key = BuildConfig.API_KEY,
                language = MoviesRemoteDataSource.MOVIE_LANGUAGE
        ).map { response -> mapResponsePersonDetailsToEntity(response) }
    }

    override fun getCastMovies(castId: Int): Single<List<MovieActorInEntity>> {

        return moviesService.getMoviesCreditsAsync(
                endpoint = BuildConfig.ENDPOINT_PERSON,
                castId = castId,
                key = BuildConfig.API_KEY,
                language = MoviesRemoteDataSource.MOVIE_LANGUAGE
        ).map { response -> mapResponseMoviesActorInToEntities(response) }
    }

}