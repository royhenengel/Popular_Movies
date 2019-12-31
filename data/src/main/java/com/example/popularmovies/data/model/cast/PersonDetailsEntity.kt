package com.example.popularmovies.data.model.cast

import java.util.*

data class PersonDetailsEntity(

    val id: Int,

    val name: String,

    val birthday: Date?,

    val deathDay: Date?,

    val biography: String,

    val imagePath: String,

    val imdbId: String

)