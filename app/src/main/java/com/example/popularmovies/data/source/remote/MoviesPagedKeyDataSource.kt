package com.example.popularmovies.data.source.remote

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.popularmovies.api.main.entity.ResponseMoviesList
import com.example.popularmovies.api.main.genre.ResponseMovieGenres
import com.example.popularmovies.data.main.entity.MovieEntity
import com.example.popularmovies.data.main.entity.MovieGenreEntity
import com.example.popularmovies.data.source.remote.mapper.ResponseMovieGenresItemToEntityMapper
import com.example.popularmovies.data.source.remote.mapper.ResponseMovieItemToEntityMapper
import io.reactivex.Single

abstract class MoviesPagedKeyDataSource(

    private val responseMovieItemToEntityMapper: ResponseMovieItemToEntityMapper

) : PageKeyedDataSource<Int, MovieEntity>() {

    val stateLiveData = MutableLiveData<STATE>()

    abstract override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, MovieEntity>)

    abstract override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieEntity>)

    abstract override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieEntity>)

    protected fun keyAfter(params: LoadParams<Int>): Int? = if (params.key > 1) params.key + 1 else null

    protected fun keyBefore(params: LoadParams<Int>): Int? = if (params.key > 1) params.key - 1 else null

    protected fun mapResponseMovieItemsToEntities(response: ResponseMoviesList): MutableList<MovieEntity> {

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

    companion object {

        const val FIRST_PAGE = 1
        const val PAGE_SIZE = 20
        const val MOVIE_LANGUAGE = "en-US"
    }
}