package com.example.popularmovies.data.main.source.remote.mapper

import androidx.arch.core.util.Function
import com.example.popularmovies.api.details.entity.cast.CastMovieItem
import com.example.popularmovies.data.details.entity.movie.CastMovieEntity
import com.example.popularmovies.data.main.entity.MovieEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResponseCastMovieItemToEntityMapper @Inject constructor() : Function<CastMovieItem, CastMovieEntity> {

    override fun apply(castMovieItem: CastMovieItem) : CastMovieEntity {

        return CastMovieEntity(
            id = castMovieItem.id ?: NULLABLE_INT_DEFAULT,
            name = castMovieItem.title ?: NULLABLE_STRING_DEFAULT,
            imagePath = castMovieItem.posterPath ?: NULLABLE_STRING_DEFAULT
        )
    }

    companion object{

        private const val NULLABLE_STRING_DEFAULT = ""
        private const val NULLABLE_INT_DEFAULT = -1
    }

}