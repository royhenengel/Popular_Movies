package com.example.popularmovies.data.main.source.remote

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.popularmovies.api.details.model.ResponseMovieDetails
import com.example.popularmovies.api.main.models.ResponseMoviesList
import com.example.popularmovies.data.details.model.MovieDetailsModel
import com.example.popularmovies.data.main.models.MovieModel
import com.example.popularmovies.data.main.source.remote.mapper.MovieDetailsResponseToModelMapper
import com.example.popularmovies.data.main.source.remote.mapper.ResponseMovieItemToModelMapper

abstract class MoviesRemoteDataSource(

        private val responseMovieItemToModelMapper: ResponseMovieItemToModelMapper,

        private val movieDetailsResponseToModelMapper: MovieDetailsResponseToModelMapper

) : PageKeyedDataSource<Int, MovieModel>() {

    val stateLiveData: MutableLiveData<STATE> = MutableLiveData()

    abstract override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, MovieModel>)

    abstract override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieModel>)

    abstract override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieModel>)

    abstract suspend fun getMovieDetails(movieId: Int): MovieDetailsModel

    protected fun keyAfter(params: LoadParams<Int>): Int? = if (params.key > 1) params.key + 1 else null

    protected fun keyBefore(params: LoadParams<Int>): Int? = if (params.key > 1) params.key - 1 else null

    protected fun mapResponseItemsToModels(response: ResponseMoviesList): MutableList<MovieModel> {

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

    protected fun mapMovieDetailsResponseToModel(response: ResponseMovieDetails): MovieDetailsModel {

        return movieDetailsResponseToModelMapper.apply(response)
    }

    enum class STATE {
        DONE, LOADING, ERROR
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