package com.example.popularmovies.data.main

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.popularmovies.data.main.models.MovieModel
import com.example.popularmovies.data.main.source.remote.MoviesRemoteDataSource
import com.example.popularmovies.data.main.source.remote.MoviesRemoteDataSourceFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(

    moviesRemoteDataSourceFactory: MoviesRemoteDataSourceFactory

){
    val moviesPagedListLiveData: LiveData<PagedList<MovieModel>> by lazy {

        val config: PagedList.Config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(MoviesRemoteDataSource.PAGE_SIZE)
            .build()

        LivePagedListBuilder(moviesRemoteDataSourceFactory, config).build()
    }

}