package com.example.popularmovies.ui.details.movie.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popularmovies.data.MoviesRepository
import com.example.popularmovies.data.details.entity.cast.ActorInMovieEntity
import com.example.popularmovies.ui.common.scrollingthumbnail.entity.ThumbnailUiEntity
import com.example.popularmovies.ui.common.scrollingthumbnail.viewmodel.ScrollingThumbnailsViewUiModel
import com.example.popularmovies.ui.details.movie.entity.MovieDetailsUiEntity
import com.example.popularmovies.ui.details.movie.entity.mapper.ActorInMovieEntityToThumbnailUiEntityMapper
import com.example.popularmovies.ui.details.movie.entity.mapper.MovieDetailsEntityToUiEntityMapper
import com.example.popularmovies.viewmodel.SingleLiveEvent
import kotlinx.coroutines.*
import javax.inject.Inject

class MovieDetailsFragmentViewModel @Inject constructor(

        private val repository: MoviesRepository,

        private val actorInMovieEntityToThumbnailUiEntityMapper: ActorInMovieEntityToThumbnailUiEntityMapper,

        private val movieDetailsEntityToUiEntityMapper: MovieDetailsEntityToUiEntityMapper

) : ViewModel() {

    val movieDetailsUiEntityLiveData = MutableLiveData<MovieDetailsUiEntity>()
    val movieCastUiModelLiveData = MutableLiveData<ScrollingThumbnailsViewUiModel>()

    val castThumbnailClickedLiveEvent = SingleLiveEvent<ActorInMovieEntity>()
    val actionHomeLiveEvent = SingleLiveEvent<Any>()

    private val uiScope = CoroutineScope(Dispatchers.Main)
    private val getMovieDetailsJob: Job = Job()

    private lateinit var actorInMovieList: List<ActorInMovieEntity>

    override fun onCleared() {
        super.onCleared()

        getMovieDetailsJob.cancel()
    }

    fun start(movieId: Int) {

        uiScope.launch {

            val detailsTask = async(Dispatchers.IO) { repository.getMovieDetails(movieId) }
            val castTask = async(Dispatchers.IO) { repository.getMovieCast(movieId) }

            val uiEntity = movieDetailsEntityToUiEntityMapper.apply(detailsTask.await())
            actorInMovieList = castTask.await()
            val thumbnails = mapActorsInMovieToThumbnails(actorInMovieList)
            val scrollingThumbnailsViewUiModel = ScrollingThumbnailsViewUiModel(thumbnails)

            movieCastUiModelLiveData.value = scrollingThumbnailsViewUiModel
            movieDetailsUiEntityLiveData.value = uiEntity
        }
    }

    fun onThumbnailClicked(position: Int) {

        val thumbnailClicked = actorInMovieList[position]
        castThumbnailClickedLiveEvent.value = thumbnailClicked
    }

    fun onToolbarHomeClicked() {

        actionHomeLiveEvent.call()
    }

    private fun mapActorsInMovieToThumbnails(actorInMovieList: List<ActorInMovieEntity>): List<ThumbnailUiEntity> {

        val thumbnails = arrayListOf<ThumbnailUiEntity>()
        for (item in actorInMovieList) {
            thumbnails.add(actorInMovieEntityToThumbnailUiEntityMapper.apply(item))
        }

        return thumbnails
    }

}
