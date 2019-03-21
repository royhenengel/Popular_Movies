package com.example.popularmovies.api.main

import com.example.popularmovies.api.main.models.ResponseMoviesList
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val PATH_ENDPOINT = "endpoint"
private const val PATH_CATEGORY = "category"

private const val QUERY_API_KEY = "api_key"
private const val QUERY_LANGUAGE = "language"
private const val QUERY_PAGE = "page"

interface MoviesService {

    @GET("{$PATH_ENDPOINT}/{$PATH_CATEGORY}")
    fun getMoviesAsync(
        @Path(PATH_ENDPOINT, encoded = true) endpoint: String,
        @Path(PATH_CATEGORY, encoded = true) category: String,
        @Query(QUERY_API_KEY) key: String,
        @Query(QUERY_LANGUAGE) language: String,
        @Query(QUERY_PAGE) page: Int

    ): Deferred<ResponseMoviesList>

}