package com.example.popularmovies.data.details.entity.cast

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