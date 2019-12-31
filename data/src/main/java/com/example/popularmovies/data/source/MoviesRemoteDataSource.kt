package com.example.popularmovies.remote

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.popularmovies.data.model.MovieEntity
import com.example.popularmovies.remote.api.main.entity.ResponseMoviesList
import com.example.popularmovies.remote.mapper.ResponseMovieItemToEntityMapper

abstract class MoviesRemoteDataSource(

        private val responseMovieItemToEntityMapper: ResponseMovieItemToEntityMapper

) : PageKeyedDataSource<Int, MovieEntity>() {

    val stateLiveData = MutableLiveData<STATE>()

    abstract override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, MovieEntity>)

    abstract override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieEntity>)

    abstract override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieEntity>)

    protected fun keyAfter(params: LoadParams<Int>): Int? = if (params.key > 1) params.key + 1 else null

    protected fun keyBefore(params: LoadParams<Int>): Int? = if (params.key > 1) params.key - 1 else null

    protected fun mapPopularMovieResponseItemsToModels(response: ResponseMoviesList): MutableList<MovieEntity> {

        val movieModelsList = arrayListOf<MovieEntity>()
        if (response.resultsList != null) {
            for (responseMovieItem in response.resultsList) {
                if (responseMovieItem?.id != null) {
                    movieModelsList.add(responseMovieItemToEntityMapper.apply(responseMovieItem))
                }
            }
        }

        return movieModelsList
    }

    enum class STATE {
        LOADED, LOADING, ERROR
    }

    enum class CATEGORY(
            val description: String
    ) {
        POPULAR("popular")
    }

    companion object {
        const val FIRST_PAGE = 1
        const val PAGE_SIZE = 20
        const val MOVIE_LANGUAGE = "en-US"
    }
}