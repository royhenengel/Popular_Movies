package com.example.popularmovies.data.source.remote

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.popularmovies.api.details.entity.cast.ResponseCastDetails
import com.example.popularmovies.api.details.entity.cast.ResponseCastMovies
import com.example.popularmovies.api.details.entity.cast.ResponseMovieCast
import com.example.popularmovies.api.details.entity.movie.ResponseMovieDetails
import com.example.popularmovies.api.main.entity.ResponseMoviesList
import com.example.popularmovies.data.details.entity.cast.PersonDetailsEntity
import com.example.popularmovies.data.details.entity.cast.ActorInMovieEntity
import com.example.popularmovies.data.details.entity.movie.MovieActorInEntity
import com.example.popularmovies.data.details.entity.movie.MovieDetailsEntity
import com.example.popularmovies.data.main.entity.MovieEntity
import com.example.popularmovies.data.source.remote.mapper.*

abstract class MoviesRemoteDataSource(

    private val responseMovieItemToEntityMapper: ResponseMovieItemToEntityMapper,

    private val responseMovieDetailsToEntityMapper: ResponseMovieDetailsToEntityMapper,

    private val responseActorInMovieItemToEntityMapper: ResponseActorInMovieItemToEntityMapper,

    private val responsePersonDetailsToEntityMapper: ResponsePersonDetailsToEntityMapper,

    private val responseMovieActorInItemToEntityMapper: ResponseMovieActorInItemToEntityMapper

) : PageKeyedDataSource<Int, MovieEntity>() {

    val stateLiveData: MutableLiveData<STATE> = MutableLiveData()

    abstract override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, MovieEntity>)

    abstract override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieEntity>)

    abstract override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieEntity>)

    abstract suspend fun getMovieDetails(movieId: Int): MovieDetailsEntity

    abstract suspend fun getMovieCast(movieId: Int): List<ActorInMovieEntity>

    abstract suspend fun getCastDetails(castId: Int): PersonDetailsEntity

    abstract suspend fun getCastMovies(castId: Int): List<MovieActorInEntity>

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

    protected fun mapMovieDetailsResponseToModel(response: ResponseMovieDetails): MovieDetailsEntity {

        return responseMovieDetailsToEntityMapper.apply(response)
    }

    protected fun mapCastResponseItemsToModels(response: ResponseMovieCast): MutableList<ActorInMovieEntity> {

        val castModelsList = arrayListOf<ActorInMovieEntity>()
        if (response.responseActorInMovieItemList != null) {
            for (responseCastItem in response.responseActorInMovieItemList) {
                if (responseCastItem?.id != null) {
                    castModelsList.add(responseActorInMovieItemToEntityMapper.apply(responseCastItem))
                }
            }
        }

        return castModelsList
    }

    protected fun mapResponseCastDetailsToEntity(response: ResponseCastDetails): PersonDetailsEntity {

        return responsePersonDetailsToEntityMapper.apply(response)
    }

    protected fun mapResponseCastMoviesToEntities(response: ResponseCastMovies): List<MovieActorInEntity>{

        val castMovieEntities = arrayListOf<MovieActorInEntity>()
        if (response.responseMovieActorInList != null) {
            for (responseCastItem in response.responseMovieActorInList) {
                if (responseCastItem?.id != null) {
                    castMovieEntities.add(responseMovieActorInItemToEntityMapper.apply(responseCastItem))
                }
            }
        }

        return castMovieEntities
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