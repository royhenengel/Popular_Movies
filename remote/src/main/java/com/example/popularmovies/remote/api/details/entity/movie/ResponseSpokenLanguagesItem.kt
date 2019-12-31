package com.example.popularmovies.remote.api.details.entity.movie

import com.google.gson.annotations.SerializedName

data class ResponseSpokenLanguagesItem(

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("iso_639_1")
    val iso6391: String? = null

)