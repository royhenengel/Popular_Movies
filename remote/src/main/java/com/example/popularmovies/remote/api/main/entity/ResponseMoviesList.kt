package com.example.popularmovies.remote.api.main.entity

import com.google.gson.annotations.SerializedName

data class ResponseMoviesList(

    @SerializedName("page")
    val page: Int? = null,

    @SerializedName("total_pages")
    val totalPages: Int? = null,

    @SerializedName("results")
    val resultsList: List<ResponseMovieItem?>? = null,

    @SerializedName("total_results")
    val totalResults: Int? = null

)