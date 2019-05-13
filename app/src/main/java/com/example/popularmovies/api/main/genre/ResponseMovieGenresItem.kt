package com.example.popularmovies.api.main.genre

import com.google.gson.annotations.SerializedName

data class ResponseMovieGenresItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null

)