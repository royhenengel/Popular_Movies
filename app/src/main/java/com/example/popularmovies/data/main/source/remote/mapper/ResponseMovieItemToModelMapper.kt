package com.example.popularmovies.data.main.source.remote.mapper

import androidx.arch.core.util.Function
import com.example.popularmovies.api.main.models.ResponseMovieItem
import com.example.popularmovies.data.main.models.MovieModel
import com.example.popularmovies.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResponseMovieItemToModelMapper @Inject constructor() : Function<ResponseMovieItem, MovieModel>{

    override fun apply(movieResponse: ResponseMovieItem) : MovieModel{

        return MovieModel(
            id = movieResponse.id!!  ,
            title = movieResponse.originalTitle,
            overview = movieResponse.overview ,
            releaseDate = dateFromString(dateString = movieResponse.releaseDate),
            score = movieResponse.voteAverage,
            thumbnailPath = movieResponse.posterPath
        )
    }

}