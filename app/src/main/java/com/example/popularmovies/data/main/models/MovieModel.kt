package com.example.popularmovies.data.main.models

import java.util.*

data class MovieModel(

    val id: Int,

    val title: String?,

    val overview: String?,

    val releaseDate: Date?,

    val voteCount: Int?,

    val popularity: Double?,

    val voteAverage: Double?,

    val isAdult : Boolean?,

    val isVideo: Boolean?,

    val posterPath: String?,

    val backdropPath: String?,

    val genreIds: List<Int?>?

)