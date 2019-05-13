package com.example.popularmovies.data.main.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "table_movies")
data class MovieEntity(

        @PrimaryKey
        val id: Int,

        val title: String?,

        val overview: String?,

        val releaseDate: Date?,

        val score: Double?,

        val thumbnailPath: String?

)

