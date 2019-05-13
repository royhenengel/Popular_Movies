package com.example.popularmovies.api.main.genre

import com.google.gson.annotations.SerializedName

data class ResponseMovieGenres(

	@field:SerializedName("genres")
	val genres: List<ResponseMovieGenresItem?>? = null

)