package com.example.popularmovies.data.source.remote.mapper

import com.example.popularmovies.api.main.genre.ResponseMovieGenresItem
import com.example.popularmovies.data.main.entity.MovieGenreEntity
import io.reactivex.functions.Function
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResponseMovieGenresItemToEntityMapper @Inject constructor() : Function<ResponseMovieGenresItem, MovieGenreEntity>/*Function<ResponseMovieGenresItem, MovieGenreEntity>*/ {

    override fun apply(responseItem: ResponseMovieGenresItem): MovieGenreEntity {

        return MovieGenreEntity(
            id = responseItem.id!!,
            name = responseItem.name ?: NULLABLE_STRING_DEFAULT
        )
    }

    companion object {

        private const val NULLABLE_STRING_DEFAULT = ""
    }

}