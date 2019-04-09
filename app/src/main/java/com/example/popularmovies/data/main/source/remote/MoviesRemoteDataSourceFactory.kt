package com.example.popularmovies.data.main.source.remote

import androidx.paging.DataSource
import com.example.popularmovies.data.main.models.MovieModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRemoteDataSourceFactory @Inject constructor(

    private val remoteDataSource: MoviesRemoteDataSourceImpl

) : DataSource.Factory<Int, MovieModel>() {

    override fun create(): DataSource<Int, MovieModel> {

        return remoteDataSource
    }

}