package com.example.popularmovies.remote

import com.example.popularmovies.data.model.cast.ActorInMovieEntity
import com.example.popularmovies.data.model.cast.PersonDetailsEntity
import com.example.popularmovies.data.model.movie.MovieActorInEntity
import com.example.popularmovies.data.model.movie.MovieDetailsEntity
import com.example.popularmovies.remote.api.MoviesService
import com.example.popularmovies.remote.mapper.ResponseActorInMovieItemToEntityMapper
import com.example.popularmovies.remote.mapper.ResponseMovieActorInItemToEntityMapper
import com.example.popularmovies.remote.mapper.ResponseMovieDetailsToEntityMapper
import com.example.popularmovies.remote.mapper.ResponsePersonDetailsToEntityMapper
import io.reactivex.Single
import javax.inject.Inject

class DetailsRemoteDataSourceImpl @Inject constructor(

    private val moviesService: MoviesService,

    responseMovieDetailsToEntityMapper: ResponseMovieDetailsToEntityMapper,

    responseActorInMovieItemToEntityMapper: ResponseActorInMovieItemToEntityMapper,

    responsePersonDetailsToEntityMapper: ResponsePersonDetailsToEntityMapper,

    responseMovieActorInItemToEntityMapper: ResponseMovieActorInItemToEntityMapper

) : DetailsRemoteDataSource(responseMovieDetailsToEntityMapper, responseActorInMovieItemToEntityMapper,
        responsePersonDetailsToEntityMapper, responseMovieActorInItemToEntityMapper) {

    private companion object {
        private const val API_KEY = "5fa91f4d299a99ecc758dfeb22e26c10"

        private const val ENDPOINT_MOVIES = "3/movie"

        private const val ENDPOINT_PERSON = "3/person"
    }

    override fun getMovieDetails(movieId: Int): Single<MovieDetailsEntity> {

        return moviesService.getMovieDetailsAsync(
                endpoint = ENDPOINT_MOVIES,
                movieId = movieId,
                key = API_KEY,
                language = LANGUAGE
        )
                .map { response -> mapMovieDetailsResponseToModel(response) }
    }

    override fun getMovieCast(movieId: Int): Single<List<ActorInMovieEntity>> {

        return moviesService.getMovieCastAsync(
                endpoint = ENDPOINT_MOVIES,
                movieId = movieId,
                key = API_KEY,
                language = LANGUAGE
        ).map { response -> mapActorsInMovieResponseItemsToEntities(response) }
    }

    override fun getCastDetails(castId: Int): Single<PersonDetailsEntity> {

        return moviesService.getCastDetailsAsync(
                endpoint = ENDPOINT_PERSON,
                castId = castId,
                key = API_KEY,
                language = LANGUAGE
        ).map { response -> mapResponsePersonDetailsToEntity(response) }
    }

    override fun getCastMovies(castId: Int): Single<List<MovieActorInEntity>> {

        return moviesService.getMoviesCreditsAsync(
                endpoint = ENDPOINT_PERSON,
                castId = castId,
                key = API_KEY,
                language = LANGUAGE
        ).map { response -> mapResponseMoviesActorInToEntities(response) }
    }

}