package com.example.popularmovies.remote.api.details.entity.movie

import com.google.gson.annotations.SerializedName

data class ResponseBelongsToCollection(

    @SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("poster_path")
    val posterPath: String? = null

)