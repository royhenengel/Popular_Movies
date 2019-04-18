package com.example.popularmovies.api.details.entity.cast

import com.google.gson.annotations.SerializedName

data class ResponseCastMovies(

    @SerializedName("cast")
    val responseMovieActorInList: List<ResponseMovieActorInItem?>? = null,

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("responseCastMovieCrewList")
    val responseCastMovieCrewList: List<ResponseCastMovieCrewItem?>? = null

)