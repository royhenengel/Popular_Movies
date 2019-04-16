package com.example.popularmovies.data.main.source.remote.mapper

import androidx.arch.core.util.Function
import com.example.popularmovies.api.details.model.cast.CastItem
import com.example.popularmovies.data.details.model.cast.CastEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResponseCastItemToEntityMapper @Inject constructor() : Function<CastItem, CastEntity> {

    override fun apply(castItem: CastItem) : CastEntity {

        return CastEntity(
                id = castItem.id!!  ,
                name = castItem.name ?: "",
                imagePath = castItem.profilePath ?: "",
                character = castItem.character ?: ""
        )
    }

}