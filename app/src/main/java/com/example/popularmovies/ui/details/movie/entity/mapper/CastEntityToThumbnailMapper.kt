package com.example.popularmovies.ui.details.movie.entity.mapper

import androidx.arch.core.util.Function
import com.example.popularmovies.data.details.entity.cast.ActorInMovieEntity
import com.example.popularmovies.ui.common.scrollingthumbnail.entity.ThumbnailUiEntity
import com.example.popularmovies.ui.common.scrollingthumbnail.entity.ThumbnailUiEntityCastUiEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CastEntityToCastThumbnailMapper @Inject constructor() : Function<ActorInMovieEntity, ThumbnailUiEntity> {

    override fun apply(actorInMovieEntity: ActorInMovieEntity): ThumbnailUiEntity {

        return ThumbnailUiEntityCastUiEntity(
                imagePath = actorInMovieEntity.imagePath,
                title = actorInMovieEntity.name,
                desc = actorInMovieEntity.character
        )
    }

}