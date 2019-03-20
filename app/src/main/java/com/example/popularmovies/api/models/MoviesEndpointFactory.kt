package com.example.popularmovies.api.models

import com.example.popularmovies.BuildConfig
import javax.inject.Inject

class MoviesEndpointFactory @Inject constructor() {

    val apiKey: String = BuildConfig.API_KEY
    val language: String = VALUE_LANGUAGE

    val endpoint_movies = BuildConfig.ENDPOINT_MOVIES

    fun createPopularMoviesEndpoint(category: CATEGORY): String {

        return "/$category.name"
    }

    companion object {

        private const val VALUE_LANGUAGE = "en-US"
    }

    enum class CATEGORY(

        val description: String

    ) {

        POPULAR("popular")
    }

}