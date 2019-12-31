package com.example.popularmovies.remote.api.details.entity.movie

import com.google.gson.annotations.SerializedName

data class ResponseProductionCountriesItem(

    @SerializedName("iso_3166_1")
    val iso31661: String? = null,

    @SerializedName("name")
    val name: String? = null

)