package com.example.popularmovies.ui.view.details.person.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.load.engine.GlideException
import com.example.popularmovies.data.MoviesRepositoryImpl
import com.example.popularmovies.data.entity.cast.PersonDetailsEntity
import com.example.popularmovies.data.entity.movie.MovieActorInEntity
import com.example.popularmovies.ui.common.scrollingthumbnail.entity.ThumbnailUiEntity
import com.example.popularmovies.ui.common.scrollingthumbnail.viewmodel.ScrollingThumbnailsViewUiModel
import com.example.popularmovies.ui.view.details.person.entity.PersonDetailsUiEntity
import com.example.popularmovies.ui.view.details.person.entity.mapper.MovieActorInEntityToThumbnailUiEntityMapper
import com.example.popularmovies.ui.view.details.person.entity.mapper.PersonDetailsEntityToUiEntityMapper
import com.example.popularmovies.ui.viewmodel.SingleLiveEvent
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class PersonDetailsFragmentViewModel @Inject constructor(

    private val repositoryImpl: MoviesRepositoryImpl,

    private val personDetailsEntityToUiEntityMapper: PersonDetailsEntityToUiEntityMapper,

    private val movieActorInEntityToThumbnailUiEntityMapper: MovieActorInEntityToThumbnailUiEntityMapper

) : ViewModel() {

    private companion object {
        private const val IMDB_BASE_URL = "http://www.imdb.com/name/"

        private const val PATTERN_MESSAGE_GETTING_DATA = "Getting person details data with id: %d"
        private const val MESSAGE_INTERNAL_ERROR = "Error getting data for person details fragment"
        private const val MESSAGE_ERROR_LOADING_IMAGE = "Error loading person details image"
    }

    val personDetailsUiEntityLiveData = MutableLiveData<PersonDetailsUiEntity>()

    val movieThumbnailsUiModelLiveData = MutableLiveData<ScrollingThumbnailsViewUiModel>()

    val stateLiveData = MutableLiveData<STATE>()

    val movieActorInClickedLiveEvent = SingleLiveEvent<MovieActorInEntity>()

    val openInBrowserLiveEvent = SingleLiveEvent<String>()

    val actionHomeLiveEvent = SingleLiveEvent<Unit>()

    val userErrorMessage = "Error loading data\nPlease check your internet connection\nand try again"

    private lateinit var personDetailsEntity: PersonDetailsEntity
    private lateinit var movieActorInList: List<MovieActorInEntity>

    private var personId: Int = -1
    private var disposable: Disposable? = null

    override fun onCleared() {
        super.onCleared()

        disposable?.dispose()
    }

    fun start(personId: Int) {

        this.personId = personId
        initData()
    }

    fun onThumbnailClicked(position: Int) {

        val thumbnailClicked = movieActorInList[position]
        movieActorInClickedLiveEvent.value = thumbnailClicked
    }

    fun onOpenInBrowserClicked() {

        if (stateLiveData.value == STATE.LOADED) {
            val url = "${IMDB_BASE_URL}${personDetailsEntity.imdbId}"
            openInBrowserLiveEvent.value = url
        }
    }

    fun onToolbarHomeClicked() {

        if (stateLiveData.value == STATE.LOADED) {
            actionHomeLiveEvent.call()
        }
    }

    fun onTryAgainBtnClicked() {

        initData()
    }

    fun onLoadPersonImageError(glideException: GlideException?) {

        Timber.e(glideException,
            MESSAGE_ERROR_LOADING_IMAGE
        )
    }

    private fun initData() {

        setStateLoading()
        val detailsSingle = repositoryImpl.getPersonDetails(personId)
        val moviesActorInSingle = repositoryImpl.getPersonMovies(personId)

        disposable = Single.zip(detailsSingle, moviesActorInSingle,
                BiFunction<PersonDetailsEntity, List<MovieActorInEntity>, RxZipResult> { details, movies ->
                    personDetailsEntity = details
                    movieActorInList = movies

                    val uiEntity = personDetailsEntityToUiEntityMapper.apply(personDetailsEntity)
                    val thumbnails = mapMoviesActorInToThumbnails(movieActorInList)
                    RxZipResult(
                        uiEntity,
                        thumbnails
                    )
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    movieThumbnailsUiModelLiveData.value = ScrollingThumbnailsViewUiModel(it.thumbnails)
                    personDetailsUiEntityLiveData.value = it.personDetailsUiEntity
                    setStateDone()

                }, {
                    setStateError(it)

                })
    }

    private fun mapMoviesActorInToThumbnails(movieActorInList: List<MovieActorInEntity>): List<ThumbnailUiEntity> {

        val thumbnails = arrayListOf<ThumbnailUiEntity>()
        for (item in movieActorInList) {
            thumbnails.add(movieActorInEntityToThumbnailUiEntityMapper.apply(item))
        }

        return thumbnails
    }

    private fun setStateLoading() {

        stateLiveData.value =
            STATE.LOADING
        Timber.d(String.format(PATTERN_MESSAGE_GETTING_DATA, personId))
    }

    private fun setStateDone() {

        stateLiveData.value =
            STATE.LOADED
    }

    private fun setStateError(throwable: Throwable) {

        Timber.e(throwable,
            MESSAGE_INTERNAL_ERROR
        )
        stateLiveData.value =
            STATE.ERROR
    }

    enum class STATE {
        LOADING, ERROR, LOADED;
    }

    private data class RxZipResult(

        val personDetailsUiEntity: PersonDetailsUiEntity,

        val thumbnails: List<ThumbnailUiEntity>

    )

}
