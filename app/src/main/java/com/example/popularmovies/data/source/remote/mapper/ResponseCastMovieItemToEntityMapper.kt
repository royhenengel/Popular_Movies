package com.example.popularmovies.data.source.remote.mapper

import androidx.arch.core.util.Function
import com.example.popularmovies.api.details.entity.cast.ResponseCastMovieItem
import com.example.popularmovies.data.details.entity.movie.MovieActorInEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResponseCastMovieItemToEntityMapper @Inject constructor() : Function<ResponseCastMovieItem, MovieActorInEntity> {

    override fun apply(responseCastMovieItem: ResponseCastMovieItem) : MovieActorInEntity {

        return MovieActorInEntity(
            id = responseCastMovieItem.id ?: NULLABLE_INT_DEFAULT,
            name = responseCastMovieItem.title ?: NULLABLE_STRING_DEFAULT,
            imagePath = responseCastMovieItem.posterPath ?: NULLABLE_STRING_DEFAULT
        )
    }

    companion object{

        private const val NULLABLE_STRING_DEFAULT = ""
        private const val NULLABLE_INT_DEFAULT = -1
    }

}