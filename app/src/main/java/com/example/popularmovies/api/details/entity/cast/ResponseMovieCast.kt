package com.example.popularmovies.api.details.entity.cast

import com.google.gson.annotations.SerializedName

data class ResponseMovieCast(

    @SerializedName("cast")
    val responseActorInMovieItemList: List<ResponseActorInMovieItem?>? = null,

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("responseCastMovieCrewList")
    val responseMovieCrewItemList: List<ResponseMovieCrewItem?>? = null

)