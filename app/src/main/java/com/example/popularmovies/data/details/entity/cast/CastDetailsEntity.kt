package com.example.popularmovies.data.details.entity.cast

import java.util.*

data class CastDetailsEntity(

    val id: Int,

    val name: String,

    val birthday: Date?,

    val deathDay: Date?,

    val biography: String,

    val imagePath: String

)