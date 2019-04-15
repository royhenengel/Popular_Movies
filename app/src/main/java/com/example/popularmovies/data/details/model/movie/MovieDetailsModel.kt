package com.example.popularmovies.data.details.model.movie

import java.util.*


data class MovieDetailsModel(

        val id: Int,

        val title: String?,

        val overview: String?,

        val releaseDate: Date?,

        val score: Double?,

        val length: Int?,

        val thumbnailPath: String?

)