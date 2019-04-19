package com.example.popularmovies.api.details.entity.cast

import com.google.gson.annotations.SerializedName

data class ResponseActorMovieInCrewItem(

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

    @SerializedName("department")
    val department: String? = null,

    @SerializedName("job")
    val job: String? = null

)