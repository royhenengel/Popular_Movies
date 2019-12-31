package com.example.popularmovies.ui.model

import java.util.*

data class MovieDetails(
    val id: Int,

    val title: String?,

    val overview: String?,

    val releaseDate: Date?,

    val score: Double?,

    val length: Int?,

    val imagePath: String?
)