package com.example.popularmovies.api.details.entity.cast

import com.google.gson.annotations.SerializedName

data class ResponseActorsInMovie(

        @SerializedName("cast")
        val responseActorsInMovieList: List<ResponseActorInMovieItem?>? = null,

        @SerializedName("id")
        val id: Int? = null,

        @SerializedName("crew")
        val responseActorsInActorMovieInCrewList: List<ResponseActorMovieInCrewItem?>? = null

)