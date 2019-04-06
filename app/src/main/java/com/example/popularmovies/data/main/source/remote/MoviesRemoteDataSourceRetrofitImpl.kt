package com.example.popularmovies.data.main.source.remote

import com.example.popularmovies.BuildConfig
import com.example.popularmovies.api.main.MoviesService
import com.example.popularmovies.api.main.models.ResponseMoviesList
import com.example.popularmovies.data.main.models.MovieModel
import com.example.popularmovies.data.main.source.remote.mapper.ResponseMovieItemToModelMapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRemoteDataSourceRetrofitImpl @Inject constructor(

    private val moviesService: MoviesService,

    responseMovieItemToModelMapper: ResponseMovieItemToModelMapper

) : MoviesRemoteDataSource(responseMovieItemToModelMapper) {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, MovieModel>) {

        stateLiveData.postValue(STATE.LOADING)

        val call = moviesService.getMovies(
            endpoint = BuildConfig.ENDPOINT_MOVIES,
            category = CATEGORY.POPULAR.description,
            key = BuildConfig.API_KEY,
            language = MOVIE_LANGUAGE,
            page = FIRST_PAGE
        )

        call.enqueue(object : Callback<ResponseMoviesList> {

            override fun onFailure(call: Call<ResponseMoviesList>, t: Throwable) {
                //TODO ("not implemented")
            }

            override fun onResponse(call: Call<ResponseMoviesList>, response: Response<ResponseMoviesList>) {

                if (response.isSuccessful && response.body()?.results != null) {
                    val movieModelsList = response.body()?.let {
                        mapResponseItemsToModels(it)
                    }
                    callback.onResult(movieModelsList!!, null, FIRST_PAGE + 1)
                    stateLiveData.postValue(STATE.DONE)
                }
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieModel>) {

        val call = moviesService.getMovies(
            endpoint = BuildConfig.ENDPOINT_MOVIES,
            category = CATEGORY.POPULAR.description,
            key = BuildConfig.API_KEY,
            language = MOVIE_LANGUAGE,
            page = FIRST_PAGE
        )

        call.enqueue(object : Callback<ResponseMoviesList> {

            override fun onFailure(call: Call<ResponseMoviesList>, t: Throwable) {
                //TODO ("not implemented")
            }

            override fun onResponse(call: Call<ResponseMoviesList>, response: Response<ResponseMoviesList>) {

                if (response.isSuccessful && response.body()?.results != null) {
                    val key = if (params.key > 1) params.key + 1 else null
                    val movieModelsList = response.body()?.let {
                        mapResponseItemsToModels(it)
                    }
                    callback.onResult(movieModelsList!!, key)
                }
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieModel>) {

        val call = moviesService.getMovies(
            endpoint = BuildConfig.ENDPOINT_MOVIES,
            category = CATEGORY.POPULAR.description,
            key = BuildConfig.API_KEY,
            language = MOVIE_LANGUAGE,
            page = FIRST_PAGE
        )

        call.enqueue(object : Callback<ResponseMoviesList> {

            override fun onFailure(call: Call<ResponseMoviesList>, t: Throwable) {
                //TODO ("not implemented")
            }

            override fun onResponse(call: Call<ResponseMoviesList>, response: Response<ResponseMoviesList>) {

                if (response.isSuccessful && response.body()?.results != null) {
                    val key = if (params.key > 1) params.key - 1 else null
                    val movieModelsList = response.body()?.let {
                        mapResponseItemsToModels(it)
                    }
                    callback.onResult(movieModelsList!!, key)
                }
            }
        })
    }
}
