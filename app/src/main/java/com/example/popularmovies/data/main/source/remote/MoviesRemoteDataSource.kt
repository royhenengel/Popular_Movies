package com.example.popularmovies.data.main.source.remote

import com.example.popularmovies.api.main.MoviesService
import com.example.popularmovies.api.models.MoviesEndpointFactory
import com.example.popularmovies.api.models.MoviesEndpointFactory.CATEGORY.POPULAR
import com.example.popularmovies.data.main.models.MovieModel
import com.example.popularmovies.data.main.source.remote.mapper.ResponseMovieItemToModelMapper
import javax.inject.Inject

class MoviesRemoteDataSource @Inject constructor(

    private val moviesService: MoviesService,

    private val moviesEndpointFactory: MoviesEndpointFactory,

    private val responseMovieItemToModelMapper: ResponseMovieItemToModelMapper

) {

    suspend fun getMovies(): ArrayList<MovieModel> {

        val response = moviesService.getMoviesAsync(
            endpoint = moviesEndpointFactory.endpoint_movies,
            category = POPULAR.description,
            key = moviesEndpointFactory.apiKey,
            language = moviesEndpointFactory.language,
            page = 1
        ).await()

        val movieModelsList = arrayListOf<MovieModel>()
        if (response.results != null) {
            for (responseMovieItem in response.results) {
                if (responseMovieItem?.id != null) {
                    movieModelsList.add(responseMovieItemToModelMapper.apply(responseMovieItem))
                }
            }
        }

        return movieModelsList

    }

}
