package com.example.popularmovies.data.main.source.remote

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.popularmovies.api.details.model.cast.ResponseMovieCast
import com.example.popularmovies.api.details.model.movie.ResponseMovieDetails
import com.example.popularmovies.api.main.models.ResponseMoviesList
import com.example.popularmovies.data.details.model.cast.CastEntity
import com.example.popularmovies.data.details.model.movie.MovieDetailsEntity
import com.example.popularmovies.data.main.models.MovieEntity
import com.example.popularmovies.data.main.source.remote.mapper.MovieDetailsResponseToEntityMapper
import com.example.popularmovies.data.main.source.remote.mapper.ResponseCastItemToEntityMapper
import com.example.popularmovies.data.main.source.remote.mapper.ResponseMovieItemToEntityMapper

abstract class MoviesRemoteDataSource(

        private val responseMovieItemToEntityMapper: ResponseMovieItemToEntityMapper,

        private val movieDetailsResponseToEntityMapper: MovieDetailsResponseToEntityMapper,

        private val responseCastItemToEntityMapper: ResponseCastItemToEntityMapper

) : PageKeyedDataSource<Int, MovieEntity>() {

    val stateLiveData: MutableLiveData<STATE> = MutableLiveData()

    abstract override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, MovieEntity>)

    abstract override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieEntity>)

    abstract override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieEntity>)

    abstract suspend fun getMovieDetails(movieId: Int): MovieDetailsEntity

    abstract suspend fun getMovieCast(movieId: Int): List<CastEntity>

    protected fun keyAfter(params: LoadParams<Int>): Int? = if (params.key > 1) params.key + 1 else null

    protected fun keyBefore(params: LoadParams<Int>): Int? = if (params.key > 1) params.key - 1 else null

    protected fun mapPopularMovieResponseItemsToModels(response: ResponseMoviesList): MutableList<MovieEntity> {

        val movieModelsList = arrayListOf<MovieEntity>()
        if (response.results != null) {
            for (responseMovieItem in response.results) {
                if (responseMovieItem?.id != null) {
                    movieModelsList.add(responseMovieItemToEntityMapper.apply(responseMovieItem))
                }
            }
        }

        return movieModelsList
    }

    protected fun mapMovieDetailsResponseToModel(response: ResponseMovieDetails): MovieDetailsEntity {

        return movieDetailsResponseToEntityMapper.apply(response)
    }

    protected fun mapCastResponseItemsToModels(response: ResponseMovieCast): MutableList<CastEntity> {

        val castModelsList = arrayListOf<CastEntity>()
        if (response.cast != null) {
            for (responseCastItem in response.cast) {
                if (responseCastItem?.id != null) {
                    castModelsList.add(responseCastItemToEntityMapper.apply(responseCastItem))
                }
            }
        }

        return castModelsList
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