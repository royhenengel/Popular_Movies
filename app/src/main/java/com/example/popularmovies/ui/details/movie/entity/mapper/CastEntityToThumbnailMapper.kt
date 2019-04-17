package com.example.popularmovies.ui.details.movie.entity.mapper

import androidx.arch.core.util.Function
import com.example.popularmovies.data.details.entity.cast.CastEntity
import com.example.popularmovies.ui.common.scrollingthumbnail.entity.ThumbnailUiEntity
import com.example.popularmovies.ui.common.scrollingthumbnail.entity.ThumbnailUiEntityCastUiEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CastEntityToCastThumbnailMapper @Inject constructor() : Function<CastEntity, ThumbnailUiEntity> {

    override fun apply(castEntity: CastEntity): ThumbnailUiEntity {

        return ThumbnailUiEntityCastUiEntity(
                imagePath = castEntity.imagePath,
                title = castEntity.name,
                desc = castEntity.character
        )
    }

}