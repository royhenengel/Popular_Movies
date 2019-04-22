package com.example.popularmovies.ui.details.movie.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.load.engine.GlideException
import com.example.popularmovies.data.MoviesRepository
import com.example.popularmovies.data.details.entity.cast.ActorInMovieEntity
import com.example.popularmovies.data.details.entity.movie.MovieDetailsEntity
import com.example.popularmovies.ui.common.scrollingthumbnail.entity.ThumbnailUiEntity
import com.example.popularmovies.ui.common.scrollingthumbnail.viewmodel.ScrollingThumbnailsViewUiModel
import com.example.popularmovies.ui.details.movie.entity.MovieDetailsUiEntity
import com.example.popularmovies.ui.details.movie.entity.mapper.ActorInMovieEntityToThumbnailUiEntityMapper
import com.example.popularmovies.ui.details.movie.entity.mapper.MovieDetailsEntityToUiEntityMapper
import com.example.popularmovies.viewmodel.SingleLiveEvent
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class MovieDetailsFragmentViewModel @Inject constructor(

        private val repository: MoviesRepository,

        private val actorInMovieEntityToThumbnailUiEntityMapper: ActorInMovieEntityToThumbnailUiEntityMapper,

        private val movieDetailsEntityToUiEntityMapper: MovieDetailsEntityToUiEntityMapper

) : ViewModel() {

    val movieDetailsUiEntityLiveData = MutableLiveData<MovieDetailsUiEntity>()
    val movieCastUiModelLiveData = MutableLiveData<ScrollingThumbnailsViewUiModel>()
    val stateLiveData = MutableLiveData<STATE>()

    val castThumbnailClickedLiveEvent = SingleLiveEvent<ActorInMovieEntity>()
    val actionHomeLiveEvent = SingleLiveEvent<Unit>()

    val userErrorMessage = MESSAGE_USER_ERROR

    private lateinit var movieDetailsEntity: MovieDetailsEntity
    private lateinit var actorInMovieList: List<ActorInMovieEntity>
    private var disposable: Disposable? = null
    private var movieId = -1

    override fun onCleared() {
        super.onCleared()

        disposable?.dispose()
    }

    fun start(movieId: Int) {

        this.movieId = movieId
        initData()
    }

    fun onThumbnailClicked(position: Int) {

        val thumbnailClicked = actorInMovieList[position]
        castThumbnailClickedLiveEvent.value = thumbnailClicked
    }

    fun onToolbarHomeClicked() {

        if (stateLiveData.value == STATE.LOADED) {
            actionHomeLiveEvent.call()
        }
    }

    fun onLoadImageError(glideException: GlideException?) {

        Timber.e(glideException, MESSAGE_ERROR_LOADING_IMAGE)
    }

    fun onTryAgainBtnClicked() {

        stateLiveData.value = STATE.LOADING
        initData()
    }

    private fun initData() {

        val detailsSingle = repository.getMovieDetails(movieId)
        val castSingle = repository.getMovieCast(movieId)

        disposable = Single.zip(detailsSingle, castSingle,
                BiFunction<MovieDetailsEntity, List<ActorInMovieEntity>, RxZipResult> { details, actors ->
                    movieDetailsEntity = details
                    actorInMovieList = actors

                    val uiEntity = movieDetailsEntityToUiEntityMapper.apply(movieDetailsEntity)
                    val thumbnails = mapActorsInMovieToThumbnails(actorInMovieList)

                    RxZipResult(thumbnails, uiEntity)
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({handleZipResult(it) }, { handleError(it) })
    }

    private fun handleZipResult(result: RxZipResult) {

        val scrollingThumbnailsViewUiModel = ScrollingThumbnailsViewUiModel(result.thumbnailUiEntities)
        movieCastUiModelLiveData.value = scrollingThumbnailsViewUiModel
        movieDetailsUiEntityLiveData.value = result.movieDetailsUiEntity
    }

    private fun handleError(throwable: Throwable) {

        Timber.e(throwable, MESSAGE_INTERNAL_ERROR)
        stateLiveData.value = STATE.ERROR
    }

    private fun mapActorsInMovieToThumbnails(actorInMovieList: List<ActorInMovieEntity>): List<ThumbnailUiEntity> {

        val thumbnails = arrayListOf<ThumbnailUiEntity>()
        for (item in actorInMovieList) {
            thumbnails.add(actorInMovieEntityToThumbnailUiEntityMapper.apply(item))
        }

        return thumbnails
    }

    private data class RxZipResult(

            val thumbnailUiEntities: List<ThumbnailUiEntity>,

            val movieDetailsUiEntity: MovieDetailsUiEntity

    )

    enum class STATE {
        LOADING, ERROR, LOADED
    }

    companion object {

        private const val MESSAGE_INTERNAL_ERROR = "Error getting data for movie details fragment"
        private const val MESSAGE_USER_ERROR = "Error loading data\nPlease check your internet connection\nand try again"
        private const val MESSAGE_ERROR_LOADING_IMAGE = "Error loading movie details image"
    }

}
