package com.example.popularmovies.data.main.source.remote

import com.example.popularmovies.api.main.MoviesService
import com.example.popularmovies.api.main.models.ResponseMoviesList
import com.example.popularmovies.api.models.MoviesEndpointFactory
import com.example.popularmovies.api.models.MoviesEndpointFactory.CATEGORY.POPULAR
import com.example.popularmovies.data.main.source.remote.mapper.ResponseMovieItemToModelMapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MoviesRemoteDataSource @Inject constructor(

    private val moviesService: MoviesService,

    private val moviesEndpointFactory: MoviesEndpointFactory,

    private val responseMovieItemToModelMapper: ResponseMovieItemToModelMapper

) {

    fun getMovies() {

        val call = moviesService.getMovies(endpoint = moviesEndpointFactory.endpoint_movies,
            category = POPULAR.description, key = moviesEndpointFactory.apiKey, language = moviesEndpointFactory.language,
            page = 1)
        call.enqueue(object : Callback<ResponseMoviesList> {

            override fun onFailure(call: Call<ResponseMoviesList>, t: Throwable) {
                try {
                    val stop = ""
                }
                catch (e: NotImplementedError) {
                    e.printStackTrace()
                }
            }

            override fun onResponse(call: Call<ResponseMoviesList>, response: Response<ResponseMoviesList>) {
                try {
                    val stop = ""
                    TODO("not implemented")
                }
                catch (e: NotImplementedError) {
                    e.printStackTrace()
                }
            }
        })
    }

}