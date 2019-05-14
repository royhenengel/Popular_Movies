package com.example.popularmovies.data.source.remote

import com.example.popularmovies.BuildConfig
import com.example.popularmovies.api.MoviesService
import com.example.popularmovies.api.details.entity.cast.ResponseActorsInMovie
import com.example.popularmovies.api.details.entity.cast.ResponseMoviesActorIn
import com.example.popularmovies.api.details.entity.cast.ResponsePersonDetails
import com.example.popularmovies.api.details.entity.movie.ResponseMovieDetails
import com.example.popularmovies.api.main.genre.ResponseMovieGenres
import com.example.popularmovies.data.details.entity.cast.ActorInMovieEntity
import com.example.popularmovies.data.details.entity.cast.PersonDetailsEntity
import com.example.popularmovies.data.details.entity.movie.MovieActorInEntity
import com.example.popularmovies.data.details.entity.movie.MovieDetailsEntity
import com.example.popularmovies.data.main.entity.MovieGenreEntity
import com.example.popularmovies.data.source.remote.mapper.*
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

abstract class MoviesRemoteDataSource(

        protected val moviesService: MoviesService,

        private val responseMovieDetailsToEntityMapper: ResponseMovieDetailsToEntityMapper,

        private val responseActorInMovieItemToEntityMapper: ResponseActorInMovieItemToEntityMapper,

        private val responsePersonDetailsToEntityMapper: ResponsePersonDetailsToEntityMapper,

        private val responseMovieActorInItemToEntityMapper: ResponseMovieActorInItemToEntityMapper,

        private val responseMovieGenresItemToEntityMapper: ResponseMovieGenresItemToEntityMapper

) {

    lateinit var movieGenresList: List<MovieGenreEntity>

    init {

        getMovieGenres()
                .subscribeOn(Schedulers.io())
                .subscribe({ movieGenresList = it }, {
                    Timber.e(it, MESSAGE_ERROR_GETTING_MOVIE_GENRES)
                    movieGenresList = listOf()
                })
    }

    abstract fun getMovieDetails(movieId: Int): Single<MovieDetailsEntity>

    abstract fun getMovieCast(movieId: Int): Single<List<ActorInMovieEntity>>

    abstract fun getCastDetails(castId: Int): Single<PersonDetailsEntity>

    abstract fun getCastMovies(castId: Int): Single<List<MovieActorInEntity>>

    protected fun mapMovieDetailsResponseToModel(response: ResponseMovieDetails): MovieDetailsEntity {

        return responseMovieDetailsToEntityMapper.apply(response)
    }

    protected fun mapActorsInMovieResponseItemsToEntities(responseActorsIn: ResponseActorsInMovie): MutableList<ActorInMovieEntity> {

        val castModelsList = arrayListOf<ActorInMovieEntity>()
        if (responseActorsIn.responseActorsInMovieList != null) {
            for (responseCastItem in responseActorsIn.responseActorsInMovieList) {
                if (responseCastItem?.id != null) {
                    castModelsList.add(responseActorInMovieItemToEntityMapper.apply(responseCastItem))
                }
            }
        }

        return castModelsList
    }

    protected fun mapResponsePersonDetailsToEntity(response: ResponsePersonDetails): PersonDetailsEntity {

        return responsePersonDetailsToEntityMapper.apply(response)
    }

    protected fun mapResponseMoviesActorInToEntities(responseActorIn: ResponseMoviesActorIn): List<MovieActorInEntity> {

        val castMovieEntities = arrayListOf<MovieActorInEntity>()
        if (responseActorIn.responseMovieActorInList != null) {
            for (responseCastItem in responseActorIn.responseMovieActorInList) {
                if (responseCastItem?.id != null) {
                    castMovieEntities.add(responseMovieActorInItemToEntityMapper.apply(responseCastItem))
                }
            }
        }

        return castMovieEntities
    }

    private fun getMovieGenres(): Single<List<MovieGenreEntity>> {

        return moviesService.getMovieGenresAsync(
                endpoint = BuildConfig.ENDPOINT_GENRES,
                key = BuildConfig.API_KEY,
                language = MoviesPagedKeyDataSource.MOVIE_LANGUAGE
        ).map { mapResponseMovieGenresItemsToEntities(it) }
    }

    private fun mapResponseMovieGenresItemsToEntities(response: ResponseMovieGenres): ArrayList<MovieGenreEntity> {

        val genresList = arrayListOf<MovieGenreEntity>()
        genresList.add(MovieGenreEntity(-1, "All Genres"))
        if (response.genres != null) {
            for (responseMovieItem in response.genres) {
                if (responseMovieItem?.id != null) {
                    genresList.add(responseMovieGenresItemToEntityMapper.apply(responseMovieItem))
                }
            }
        }

        return genresList
    }

    enum class CATEGORY(

            val description: String

    ) {

        LATEST("Latest"),
        NOW_PLAYING("Now Playing"),
        TOP_RATED("Top Rated"),
        UPCOMING("Upcoming"),
        POPULAR("Popular");

        fun fromDescription(desc: String): CATEGORY? {

            for (category in values()){
                if (category.description == desc){
                    return category
                }
            }

            return null
        }
    }

    companion object {

        const val LANGUAGE = "en-US"

        private const val MESSAGE_ERROR_GETTING_MOVIE_GENRES = "Error fetching movie genres"
    }
}