package com.example.popularmovies.data.source.remote

import com.example.popularmovies.api.details.entity.cast.ResponseActorsInMovie
import com.example.popularmovies.api.details.entity.cast.ResponseMoviesActorIn
import com.example.popularmovies.api.details.entity.cast.ResponsePersonDetails
import com.example.popularmovies.api.details.entity.movie.ResponseMovieDetails
import com.example.popularmovies.data.details.entity.cast.ActorInMovieEntity
import com.example.popularmovies.data.details.entity.cast.PersonDetailsEntity
import com.example.popularmovies.data.details.entity.movie.MovieActorInEntity
import com.example.popularmovies.data.details.entity.movie.MovieDetailsEntity
import com.example.popularmovies.data.source.remote.mapper.ResponseActorInMovieItemToEntityMapper
import com.example.popularmovies.data.source.remote.mapper.ResponseMovieActorInItemToEntityMapper
import com.example.popularmovies.data.source.remote.mapper.ResponseMovieDetailsToEntityMapper
import com.example.popularmovies.data.source.remote.mapper.ResponsePersonDetailsToEntityMapper
import io.reactivex.Single

abstract class DetailsRemoteDataSource(

        private val responseMovieDetailsToEntityMapper: ResponseMovieDetailsToEntityMapper,

        private val responseActorInMovieItemToEntityMapper: ResponseActorInMovieItemToEntityMapper,

        private val responsePersonDetailsToEntityMapper: ResponsePersonDetailsToEntityMapper,

        private val responseMovieActorInItemToEntityMapper: ResponseMovieActorInItemToEntityMapper
) {

    abstract suspend fun getMovieDetails(movieId: Int): MovieDetailsEntity

    abstract suspend fun getMovieCast(movieId: Int): List<ActorInMovieEntity>

    abstract fun getCastDetails(castId: Int): Single<PersonDetailsEntity>

    abstract fun getCastMovies(castId: Int): Single<List<MovieActorInEntity>>

    protected fun mapMovieDetailsResponseToModel(response: ResponseMovieDetails): MovieDetailsEntity {

        return responseMovieDetailsToEntityMapper.apply(response)
    }

    protected fun mapActorsInMovieResponseItemsToEntities(responseActorsIn: ResponseActorsInMovie): MutableList<ActorInMovieEntity> {

        val castModelsList = arrayListOf<ActorInMovieEntity>()
        if (responseActorsIn.responseActorsInMovieList != null) {
            for (responseCastItem in responseActorsIn.responseActorsInMovieList) {
                if (responseCastItem?.id != null) {
                    castModelsList.add(responseActorInMovieItemToEntityMapper.apply(responseCastItem))
                }
            }
        }

        return castModelsList
    }

    protected fun mapResponsePersonDetailsToEntity(response: ResponsePersonDetails): PersonDetailsEntity {

        return responsePersonDetailsToEntityMapper.apply(response)
    }

    protected fun mapResponseMoviesActorInToEntities(responseActorIn: ResponseMoviesActorIn): List<MovieActorInEntity> {

        val castMovieEntities = arrayListOf<MovieActorInEntity>()
        if (responseActorIn.responseMovieActorInList != null) {
            for (responseCastItem in responseActorIn.responseMovieActorInList) {
                if (responseCastItem?.id != null) {
                    castMovieEntities.add(responseMovieActorInItemToEntityMapper.apply(responseCastItem))
                }
            }
        }

        return castMovieEntities
    }

    companion object {

        const val LANGUAGE = "en-US"
    }
}