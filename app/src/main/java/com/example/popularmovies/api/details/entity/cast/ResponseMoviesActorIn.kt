package com.example.popularmovies.api.details.entity.cast

import com.google.gson.annotations.SerializedName

data class ResponseMoviesActorIn(

    @SerializedName("cast")
    val responseMovieActorInList: List<ResponseMovieActorInItem?>? = null,

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("crew")
    val responseMoviesActorInCrewList: List<ResponseMoviesActorInCrewItem?>? = null

)