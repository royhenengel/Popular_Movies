package com.example.popularmovies.data.main

import com.example.popularmovies.data.main.models.MovieModel
import com.example.popularmovies.data.main.source.remote.MoviesRemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviesRepository @Inject constructor(

    private val remoteDataSource: MoviesRemoteDataSource

){

    suspend fun getMovies(): ArrayList<MovieModel> {

        return remoteDataSource.getMovies()
    }

}