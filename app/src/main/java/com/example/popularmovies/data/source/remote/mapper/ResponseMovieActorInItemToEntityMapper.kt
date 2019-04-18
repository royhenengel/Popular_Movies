package com.example.popularmovies.data.source.remote.mapper

import androidx.arch.core.util.Function
import com.example.popularmovies.api.details.entity.cast.ResponseMovieActorInItem
import com.example.popularmovies.data.details.entity.movie.MovieActorInEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResponseMovieActorInItemToEntityMapper @Inject constructor() : Function<ResponseMovieActorInItem, MovieActorInEntity> {

    override fun apply(responseMovieActorInItem: ResponseMovieActorInItem) : MovieActorInEntity {

        return MovieActorInEntity(
            id = responseMovieActorInItem.id ?: NULLABLE_INT_DEFAULT,
            name = responseMovieActorInItem.title ?: NULLABLE_STRING_DEFAULT,
            imagePath = responseMovieActorInItem.posterPath ?: NULLABLE_STRING_DEFAULT
        )
    }

    companion object{

        private const val NULLABLE_STRING_DEFAULT = ""
        private const val NULLABLE_INT_DEFAULT = -1
    }

}