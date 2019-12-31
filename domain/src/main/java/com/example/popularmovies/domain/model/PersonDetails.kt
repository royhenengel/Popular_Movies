package com.example.popularmovies.ui.model

import java.util.*

data class PersonDetails(
    val id: Int,

    val name: String,

    val birthday: Date?,

    val deathDay: Date?,

    val biography: String,

    val imagePath: String,

    val imdbId: String
)