package com.example.popularmovies.api.main.genre

import com.google.gson.annotations.SerializedName

data class ResponseGenres(

	@field:SerializedName("genres")
	val genres: List<GenresItem?>? = null

)