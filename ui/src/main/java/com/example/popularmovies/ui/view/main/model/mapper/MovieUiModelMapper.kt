package com.example.popularmovies.ui.view.main.model.mapper

import androidx.arch.core.util.Function
import com.example.popularmovies.ui.model.Movie
import com.example.popularmovies.ui.util.dateAsString
import com.example.popularmovies.ui.view.main.model.MovieUiModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieUiModelMapper @Inject constructor() : Function<Movie, MovieUiModel> {

    override fun apply(movieEntity: Movie): MovieUiModel {

        return MovieUiModel(
            id = movieEntity.id,
            title = movieEntity.title ?: NULLABLE_STRING_DEFAULT,
            overview = movieEntity.overview ?: NULLABLE_STRING_DEFAULT,
            releaseDate = dateAsString(movieEntity.releaseDate, PATTERN_YEAR) ?: NULLABLE_STRING_DEFAULT,
            score = movieEntity.score ?: NULLABLE_DOUBLE_DEFAULT,
            thumbnailPath = movieEntity.thumbnailPath ?: NULLABLE_STRING_DEFAULT
        )
    }

    companion object {
        private const val NULLABLE_STRING_DEFAULT = ""

        private const val NULLABLE_DOUBLE_DEFAULT = -1.0

        private const val PATTERN_YEAR = "yyyy"
    }
}