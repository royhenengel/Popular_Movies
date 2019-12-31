package com.example.popularmovies.ui.view.main.model

data class MovieUiModel(

    val id: Int,

    val title: String,

    val overview: String,

    val releaseDate: String,

    val score: Double,

    val thumbnailPath: String
)