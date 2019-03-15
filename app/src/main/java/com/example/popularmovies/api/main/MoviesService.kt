package com.example.popularmovies.api.main

import com.example.popularmovies.api.main.models.ResponseMoviesList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesService {

    @GET("{endpoint}")
    fun getMovies(@Path("endpoint") endpoint: String): Call<ResponseMoviesList>

}