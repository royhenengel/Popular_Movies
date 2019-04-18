package com.example.popularmovies.api.details.entity.cast

import com.google.gson.annotations.SerializedName

data class ResponseActorInMovieItem(

    @SerializedName("cast_id")
    val castId: Int? = null,

    @SerializedName("character")
    val character: String? = null,

    @SerializedName("gender")
    val gender: Int? = null,

    @SerializedName("credit_id")
    val creditId: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("profile_path")
    val profilePath: String? = null,

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("order")
    val order: Int? = null

)