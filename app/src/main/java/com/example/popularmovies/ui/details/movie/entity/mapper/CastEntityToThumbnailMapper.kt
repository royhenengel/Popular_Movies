package com.example.popularmovies.ui.details.movie.entity.mapper

import androidx.arch.core.util.Function
import com.example.popularmovies.data.details.model.cast.CastModel
import com.example.popularmovies.ui.common.scrollingthumbnail.model.Thumbnail
import com.example.popularmovies.ui.common.scrollingthumbnail.model.ThumbnailCast
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CastEntityToCastThumbnailMapper @Inject constructor() : Function<CastModel, Thumbnail> {

    override fun apply(castModel: CastModel): Thumbnail {

        return ThumbnailCast(
                imagePath = castModel.imagePath,
                title = castModel.name,
                desc = castModel.character
        )
    }

}