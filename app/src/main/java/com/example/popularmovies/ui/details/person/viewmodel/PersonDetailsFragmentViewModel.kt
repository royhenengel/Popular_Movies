package com.example.popularmovies.ui.details.person.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popularmovies.BuildConfig
import com.example.popularmovies.data.MoviesRepository
import com.example.popularmovies.data.details.entity.cast.PersonDetailsEntity
import com.example.popularmovies.data.details.entity.movie.MovieActorInEntity
import com.example.popularmovies.ui.common.scrollingthumbnail.entity.ThumbnailUiEntity
import com.example.popularmovies.ui.common.scrollingthumbnail.viewmodel.ScrollingThumbnailsViewUiModel
import com.example.popularmovies.ui.details.person.entity.PersonDetailsUiEntity
import com.example.popularmovies.ui.details.person.entity.mapper.MovieActorInEntityToThumbnailUiEntityMapper
import com.example.popularmovies.ui.details.person.entity.mapper.PersonDetailsEntityToUiEntityMapper
import com.example.popularmovies.viewmodel.SingleLiveEvent
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PersonDetailsFragmentViewModel @Inject constructor(

    private val repository: MoviesRepository,

    private val personDetailsEntityToUiEntityMapper: PersonDetailsEntityToUiEntityMapper,

    private val movieActorInEntityToThumbnailUiEntityMapper: MovieActorInEntityToThumbnailUiEntityMapper

) : ViewModel() {

    val personDetailsUiEntityLiveData = MutableLiveData<PersonDetailsUiEntity>()
    val movieThumbnailsUiModelLiveData = MutableLiveData<ScrollingThumbnailsViewUiModel>()

    val movieActorInClickedLiveEvent = SingleLiveEvent<MovieActorInEntity>()
    val openInBrowserLiveEvent = SingleLiveEvent<String>()
    val actionHomeLiveEvent = SingleLiveEvent<Unit>()

    private lateinit var personDetailsEntity: PersonDetailsEntity
    private lateinit var movieActorInList: List<MovieActorInEntity>

    private var disposable: Disposable? = null

    fun start(personId: Int) {

        val detailsSingle = repository.getPersonDetails(personId)
        val moviesActorInSingle = repository.getPersonMovies(personId)

        disposable = Single.zip(detailsSingle, moviesActorInSingle,
            BiFunction<PersonDetailsEntity, List<MovieActorInEntity>, RxZipResult> { details, movies ->
                personDetailsEntity = details
                movieActorInList = movies

                val uiEntity = personDetailsEntityToUiEntityMapper.apply(personDetailsEntity)
                val thumbnails = mapMoviesActorInToThumbnails(movieActorInList)
                RxZipResult(uiEntity, thumbnails)
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    movieThumbnailsUiModelLiveData.value = ScrollingThumbnailsViewUiModel(it.thumbnails)
                    personDetailsUiEntityLiveData.value = it.personDetailsUiEntity
                },
                {

                })
    }

    override fun onCleared() {
        super.onCleared()

        disposable?.dispose()
    }

    fun onThumbnailClicked(position: Int) {

        val thumbnailClicked = movieActorInList[position]
        movieActorInClickedLiveEvent.value = thumbnailClicked
    }

    fun onOpenInBrowserClicked() {

        val url = "${BuildConfig.IMDB_BASE_URL}${personDetailsEntity.imdbId}"
        openInBrowserLiveEvent.value = url
    }

    fun onToolbarHomeClicked() {

        actionHomeLiveEvent.call()
    }

    private fun mapMoviesActorInToThumbnails(movieActorInList: List<MovieActorInEntity>): List<ThumbnailUiEntity> {

        val thumbnails = arrayListOf<ThumbnailUiEntity>()
        for (item in movieActorInList) {
            thumbnails.add(movieActorInEntityToThumbnailUiEntityMapper.apply(item))
        }

        return thumbnails
    }

    private data class RxZipResult(

        val personDetailsUiEntity: PersonDetailsUiEntity,

        val thumbnails: List<ThumbnailUiEntity>

    )

}
