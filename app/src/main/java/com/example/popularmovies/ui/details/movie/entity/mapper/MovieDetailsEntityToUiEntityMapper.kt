package com.example.popularmovies.ui.details.movie.entity.mapper

import androidx.arch.core.util.Function
import com.example.popularmovies.BuildConfig
import com.example.popularmovies.data.details.entity.movie.MovieDetailsEntity
import com.example.popularmovies.ui.details.movie.entity.MovieDetailsUiEntity
import com.example.popularmovies.util.dateAsString
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDetailsEntityToUiEntityMapper @Inject constructor() : Function<MovieDetailsEntity, MovieDetailsUiEntity> {

    override fun apply(movieDetails: MovieDetailsEntity): MovieDetailsUiEntity {

        return MovieDetailsUiEntity(
                title = movieDetails.title ?: NULLABLE_STRING_DEFAULT,
                year = dateAsString(movieDetails.releaseDate, PATTERN_YEAR) ?: NULLABLE_STRING_DEFAULT,
                overview = movieDetails.overview ?: NULLABLE_STRING_DEFAULT,
                score = movieDetails.score.toString(),
                length = String.format(PATTERN_MOVIE_LENGTH, movieDetails.length),
                imageUrl = "${BuildConfig.MOVIES_IMAGE_BASE_URL}${movieDetails.imagePath}"
        )
    }

    companion object{

        private const val NULLABLE_STRING_DEFAULT = ""
        private const val PATTERN_YEAR = "yyyy"
        private const val PATTERN_MOVIE_LENGTH = "%d min"
    }
}