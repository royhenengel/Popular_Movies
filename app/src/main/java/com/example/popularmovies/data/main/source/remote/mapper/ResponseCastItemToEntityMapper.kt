package com.example.popularmovies.data.main.source.remote.mapper

import androidx.arch.core.util.Function
import com.example.popularmovies.api.details.entity.cast.ResponseCastMemberItem
import com.example.popularmovies.data.details.entity.cast.CastEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResponseCastItemToEntityMapper @Inject constructor() : Function<ResponseCastMemberItem, CastEntity> {

    override fun apply(responseCastMemberItem: ResponseCastMemberItem) : CastEntity {

        return CastEntity(
                id = responseCastMemberItem.id!!  ,
                name = responseCastMemberItem.name ?: "",
                imagePath = responseCastMemberItem.profilePath ?: "",
                character = responseCastMemberItem.character ?: ""
        )
    }

}