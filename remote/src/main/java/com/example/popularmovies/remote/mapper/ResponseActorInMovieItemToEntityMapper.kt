package com.example.popularmovies.remote.mapper

import androidx.arch.core.util.Function
import com.example.popularmovies.api.details.entity.cast.ResponseActorInMovieItem
import com.example.popularmovies.data.model.cast.ActorInMovieEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResponseActorInMovieItemToEntityMapper @Inject constructor() : Function<ResponseActorInMovieItem, ActorInMovieEntity> {

    override fun apply(responseActorInMovieItem: ResponseActorInMovieItem) : ActorInMovieEntity {

        return ActorInMovieEntity(
            id = responseActorInMovieItem.id
                ?: NULLABLE_INT_DEFAULT,
            name = responseActorInMovieItem.name
                ?: NULLABLE_STRING_DEFAULT,
            imagePath = responseActorInMovieItem.profilePath
                ?: NULLABLE_STRING_DEFAULT,
            character = responseActorInMovieItem.character
                ?: NULLABLE_STRING_DEFAULT
        )
    }

    companion object{

        private const val NULLABLE_STRING_DEFAULT = ""
        private const val NULLABLE_INT_DEFAULT = -1
    }

}