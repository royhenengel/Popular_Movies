package com.example.popularmovies.data.source.remote.mapper

import androidx.arch.core.util.Function
import com.example.popularmovies.api.main.entity.ResponseMovieItem
import com.example.popularmovies.data.main.entity.MovieEntity
import com.example.popularmovies.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResponseMovieItemToEntityMapper @Inject constructor() : Function<ResponseMovieItem, MovieEntity>{

    override fun apply(movieResponse: ResponseMovieItem) : MovieEntity{

        return MovieEntity(
            id = movieResponse.id!!  ,
            title = movieResponse.originalTitle,
            overview = movieResponse.overview ,
            releaseDate = dateFromString(dateString = movieResponse.releaseDate),
            score = movieResponse.voteAverage,
            thumbnailPath = movieResponse.posterPath
        )
    }

}