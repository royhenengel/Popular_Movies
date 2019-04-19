package com.example.popularmovies.ui.details.person.entity.mapper

import androidx.arch.core.util.Function
import com.example.popularmovies.data.details.entity.movie.MovieActorInEntity
import com.example.popularmovies.ui.common.scrollingthumbnail.entity.ThumbnailUiEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieActorInEntityToThumbnailUiEntityMapper @Inject constructor() : Function<MovieActorInEntity, ThumbnailUiEntity> {

    override fun apply(movieActorInEntity: MovieActorInEntity): ThumbnailUiEntity {

        return ThumbnailUiEntity(
                imagePath = movieActorInEntity.imagePath,
                title = movieActorInEntity.name
        )
    }

}