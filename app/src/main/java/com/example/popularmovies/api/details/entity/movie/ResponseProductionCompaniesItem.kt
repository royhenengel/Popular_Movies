package com.example.popularmovies.api.details.entity.movie

import com.google.gson.annotations.SerializedName

data class ResponseProductionCompaniesItem(

    @SerializedName("logo_path")
    val logoPath: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("origin_country")
    val originCountry: String? = null

)