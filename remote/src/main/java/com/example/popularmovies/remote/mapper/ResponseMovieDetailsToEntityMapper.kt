package com.example.popularmovies.remote.mapper

import androidx.arch.core.util.Function
import com.example.popularmovies.api.details.entity.movie.ResponseMovieDetails
import com.example.popularmovies.data.model.movie.MovieDetailsEntity
import com.example.popularmovies.ui.util.dateFromString
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResponseMovieDetailsToEntityMapper @Inject constructor() : Function<ResponseMovieDetails, MovieDetailsEntity> {

    override fun apply(movieDetailsResponse: ResponseMovieDetails): MovieDetailsEntity {

        return MovieDetailsEntity(
            id = movieDetailsResponse.id!!,
            title = movieDetailsResponse.title,
            overview = movieDetailsResponse.overview,
            releaseDate = com.example.popularmovies.ui.util.dateFromString(
                dateString = movieDetailsResponse.releaseDate
            ),
            score = movieDetailsResponse.voteAverage,
            length = movieDetailsResponse.runtime,
            imagePath = movieDetailsResponse.backdropPath
        )
    }

}