package com.example.popularmovies.ui.details.movie.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popularmovies.data.details.model.movie.MovieDetailsModel
import com.example.popularmovies.data.MoviesRepository
import com.example.popularmovies.data.details.model.cast.CastModel
import com.example.popularmovies.data.main.models.MovieModel
import com.example.popularmovies.ui.common.scrollingthumbnail.model.ScrollingThumbnailClickListener
import com.example.popularmovies.ui.common.scrollingthumbnail.model.Thumbnail
import com.example.popularmovies.ui.common.scrollingthumbnail.viewmodel.ScrollingThumbnailsViewUiModel
import com.example.popularmovies.ui.details.movie.entity.MovieDetailsUiEntity
import com.example.popularmovies.ui.details.movie.entity.mapper.CastEntityToCastThumbnailMapper
import com.example.popularmovies.ui.details.movie.entity.mapper.MovieDetailsEntityToUiEntityMapper
import com.example.popularmovies.viewmodel.SingleLiveEvent
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.coroutines.*
import javax.inject.Inject

class MovieDetailsFragmentViewModel @Inject constructor(

        private val repository: MoviesRepository,

        private val castEntityToCastThumbnailMapper: CastEntityToCastThumbnailMapper,

        private val movieDetailsEntityToUiEntityMapper: MovieDetailsEntityToUiEntityMapper

) : ViewModel() {

    val movieDetailsUiEntityLiveData = MutableLiveData<MovieDetailsUiEntity>()
    val movieCastUiModelLiveData = MutableLiveData<ScrollingThumbnailsViewUiModel>()

    val castThumbnailClickedLiveEvent = SingleLiveEvent<CastModel>()

    private val uiScope = CoroutineScope(Dispatchers.Main)
    private val getMovieDetailsJob: Job = Job()

    private lateinit var castList: List<CastModel>

    override fun onCleared() {
        super.onCleared()

        getMovieDetailsJob?.cancel()
    }

    fun start(movieId: Int) {

        uiScope.launch {

            val detailsTask = async(Dispatchers.IO) { repository.getMovieDetails(movieId) }
            val castTask = async(Dispatchers.IO) { repository.getMovieCast(movieId) }

            val uiEntity = movieDetailsEntityToUiEntityMapper.apply(detailsTask.await())
            castList = castTask.await()
            val thumbnails = mapCastItemsToThumbnails(castList)

            val scrollingThumbnailsViewUiModel = ScrollingThumbnailsViewUiModel(thumbnails)

            movieCastUiModelLiveData.value = scrollingThumbnailsViewUiModel
            movieDetailsUiEntityLiveData.value = uiEntity
        }
    }

    fun onThumbnailClicked(position: Int) {

        val thumbnailClicked = castList[position]
        castThumbnailClickedLiveEvent.value = thumbnailClicked
    }

    private fun mapCastItemsToThumbnails(castItems: List<CastModel>): List<Thumbnail> {

        val thumbnails = arrayListOf<Thumbnail>()
        for (item in castItems) {
            thumbnails.add(castEntityToCastThumbnailMapper.apply(item))
        }

        return thumbnails
    }

}
