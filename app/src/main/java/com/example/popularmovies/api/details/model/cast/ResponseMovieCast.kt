package com.example.popularmovies.api.details.model.cast

import com.google.gson.annotations.SerializedName

data class ResponseMovieCast(

	@field:SerializedName("cast")
	val cast: List<CastItem?>? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("crew")
	val crew: List<CrewItem?>? = null
)