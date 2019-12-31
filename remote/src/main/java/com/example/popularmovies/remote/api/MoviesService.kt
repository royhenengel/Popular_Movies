package com.example.popularmovies.remote.api

import com.example.popularmovies.remote.api.details.entity.cast.ResponsePersonDetails
import com.example.popularmovies.remote.api.details.entity.cast.ResponseMoviesActorIn
import com.example.popularmovies.remote.api.details.entity.cast.ResponseActorsInMovie
import com.example.popularmovies.remote.api.details.entity.movie.ResponseMovieDetails
import com.example.popularmovies.remote.api.main.entity.ResponseMoviesList
import io.reactivex.Single
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {

    @GET("{$PATH_ENDPOINT}/{$PATH_CATEGORY}")
    fun getMoviesAsync(
        @Path(PATH_ENDPOINT, encoded = true) endpoint: String,
        @Path(PATH_CATEGORY, encoded = true) category: String,
        @Query(QUERY_API_KEY) key: String,
        @Query(QUERY_LANGUAGE) language: String,
        @Query(QUERY_PAGE) page: Int

    ): Single<ResponseMoviesList>

    @GET("{$PATH_ENDPOINT}/{$PATH_ID}")
    fun getMovieDetailsAsync(
        @Path(PATH_ENDPOINT, encoded = true) endpoint: String,
        @Path(PATH_ID, encoded = true) movieId: Int,
        @Query(QUERY_API_KEY) key: String,
        @Query(QUERY_LANGUAGE) language: String

    ) : Single<ResponseMovieDetails>

    @GET("{$PATH_ENDPOINT}/{$PATH_ID}/casts")
    fun getMovieCastAsync(
        @Path(PATH_ENDPOINT, encoded = true) endpoint: String,
        @Path(PATH_ID, encoded = true) movieId: Int,
        @Query(QUERY_API_KEY) key: String,
        @Query(QUERY_LANGUAGE) language: String

    ) : Single<ResponseActorsInMovie>

    @GET("{$PATH_ENDPOINT}/{$PATH_ID}")
    fun getCastDetailsAsync(
        @Path(PATH_ENDPOINT, encoded = true) endpoint: String,
        @Path(PATH_ID, encoded = true) castId: Int,
        @Query(QUERY_API_KEY) key: String,
        @Query(QUERY_LANGUAGE) language: String

    ) : Single<ResponsePersonDetails>

    @GET("{$PATH_ENDPOINT}/{$PATH_ID}/movie_credits")
    fun getMoviesCreditsAsync(
        @Path(PATH_ENDPOINT, encoded = true) endpoint: String,
        @Path(PATH_ID, encoded = true) castId: Int,
        @Query(QUERY_API_KEY) key: String,
        @Query(QUERY_LANGUAGE) language: String

    ) : Single<ResponseMoviesActorIn>

    companion object {
        private const val PATH_ENDPOINT = "endpoint"
        private const val PATH_CATEGORY = "category"
        private const val PATH_ID = "id"

        private const val QUERY_API_KEY = "api_key"
        private const val QUERY_LANGUAGE = "language"
        private const val QUERY_PAGE = "page"
    }

}