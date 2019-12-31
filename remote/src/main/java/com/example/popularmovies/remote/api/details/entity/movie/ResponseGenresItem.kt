package com.example.popularmovies.remote.api.details.entity.movie

import com.google.gson.annotations.SerializedName

data class ResponseGenresItem(

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("id")
    val id: Int? = null

)