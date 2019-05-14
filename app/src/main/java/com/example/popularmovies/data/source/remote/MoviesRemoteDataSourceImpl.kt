package com.example.popularmovies.data.source.remote

import com.example.popularmovies.BuildConfig
import com.example.popularmovies.api.MoviesService
import com.example.popularmovies.data.details.entity.cast.ActorInMovieEntity
import com.example.popularmovies.data.details.entity.cast.PersonDetailsEntity
import com.example.popularmovies.data.details.entity.movie.MovieActorInEntity
import com.example.popularmovies.data.details.entity.movie.MovieDetailsEntity
import com.example.popularmovies.data.main.entity.MovieGenreEntity
import com.example.popularmovies.data.source.remote.mapper.*
import io.reactivex.Single
import javax.inject.Inject

class MoviesRemoteDataSourceImpl @Inject constructor(

        moviesService: MoviesService,

        responseMovieDetailsToEntityMapper: ResponseMovieDetailsToEntityMapper,

        responseActorInMovieItemToEntityMapper: ResponseActorInMovieItemToEntityMapper,

        responsePersonDetailsToEntityMapper: ResponsePersonDetailsToEntityMapper,

        responseMovieActorInItemToEntityMapper: ResponseMovieActorInItemToEntityMapper,

        responseMovieGenresItemToEntityMapper: ResponseMovieGenresItemToEntityMapper

) : MoviesRemoteDataSource(moviesService, responseMovieDetailsToEntityMapper, responseActorInMovieItemToEntityMapper,
        responsePersonDetailsToEntityMapper, responseMovieActorInItemToEntityMapper, responseMovieGenresItemToEntityMapper) {

    override fun getMovieDetails(movieId: Int): Single<MovieDetailsEntity> {

        return moviesService.getMovieDetailsAsync(
                endpoint = BuildConfig.ENDPOINT_MOVIES,
                movieId = movieId,
                key = BuildConfig.API_KEY,
                language = LANGUAGE
        )
                .map { response -> mapMovieDetailsResponseToModel(response) }
    }

    override fun getMovieCast(movieId: Int): Single<List<ActorInMovieEntity>> {

        return moviesService.getMovieCastAsync(
                endpoint = BuildConfig.ENDPOINT_MOVIES,
                movieId = movieId,
                key = BuildConfig.API_KEY,
                language = LANGUAGE
        ).map { response -> mapActorsInMovieResponseItemsToEntities(response) }
    }

    override fun getCastDetails(castId: Int): Single<PersonDetailsEntity> {

        return moviesService.getCastDetailsAsync(
                endpoint = BuildConfig.ENDPOINT_PERSON,
                castId = castId,
                key = BuildConfig.API_KEY,
                language = LANGUAGE
        ).map { response -> mapResponsePersonDetailsToEntity(response) }
    }

    override fun getCastMovies(castId: Int): Single<List<MovieActorInEntity>> {

        return moviesService.getMoviesCreditsAsync(
                endpoint = BuildConfig.ENDPOINT_PERSON,
                castId = castId,
                key = BuildConfig.API_KEY,
                language = LANGUAGE
        ).map { response -> mapResponseMoviesActorInToEntities(response) }
    }

}