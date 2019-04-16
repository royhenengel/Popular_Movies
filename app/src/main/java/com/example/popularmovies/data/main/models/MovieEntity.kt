package com.example.popularmovies.data.main.models

import java.util.*

data class MovieEntity(

        val id: Int,

        val title: String?,

        val overview: String?,

        val releaseDate: Date?,

        val score: Double?,

        val thumbnailPath: String?

)