package com.example.popularmovies.data.main.source.remote.mapper

import androidx.arch.core.util.Function
import com.example.popularmovies.api.details.model.cast.CastItem
import com.example.popularmovies.data.details.model.cast.CastModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResponseCastItemToModelMapper @Inject constructor() : Function<CastItem, CastModel> {

    override fun apply(castItem: CastItem) : CastModel {

        return CastModel(
                id = castItem.id!!  ,
                name = castItem.name ?: "",
                imagePath = castItem.profilePath ?: "",
                character = castItem.character ?: ""
        )
    }

}