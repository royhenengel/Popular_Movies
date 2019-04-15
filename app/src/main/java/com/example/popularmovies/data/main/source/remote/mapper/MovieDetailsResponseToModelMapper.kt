package com.example.popularmovies.data.main.source.remote.mapper

import androidx.arch.core.util.Function
import com.example.popularmovies.api.details.model.movie.ResponseMovieDetails
import com.example.popularmovies.data.details.model.movie.MovieDetailsModel
import com.example.popularmovies.util.dateFromString
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDetailsResponseToModelMapper @Inject constructor() : Function<ResponseMovieDetails, MovieDetailsModel> {

    override fun apply(movieDetailsResponse: ResponseMovieDetails): MovieDetailsModel {

        return MovieDetailsModel(
                id = movieDetailsResponse.id!!,
                title = movieDetailsResponse.title,
                overview = movieDetailsResponse.overview,
                releaseDate = dateFromString(dateString = movieDetailsResponse.releaseDate),
                score = movieDetailsResponse.voteAverage,
                length = movieDetailsResponse.runtime,
                thumbnailPath = movieDetailsResponse.backdropPath
        )
    }

}