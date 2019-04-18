package com.example.popularmovies.data.source.remote.mapper

import androidx.arch.core.util.Function
import com.example.popularmovies.api.details.entity.cast.ResponseCastMemberItem
import com.example.popularmovies.data.details.entity.cast.ActorInMovieEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResponseCastItemToEntityMapper @Inject constructor() : Function<ResponseCastMemberItem, ActorInMovieEntity> {

    override fun apply(responseCastMemberItem: ResponseCastMemberItem) : ActorInMovieEntity {

        return ActorInMovieEntity(
                id = responseCastMemberItem.id!!  ,
                name = responseCastMemberItem.name ?: "",
                imagePath = responseCastMemberItem.profilePath ?: "",
                character = responseCastMemberItem.character ?: ""
        )
    }

}