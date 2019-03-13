package com.example.popularmovies.data.main

import com.example.popularmovies.data.main.source.remote.MoviesRemoteDataSource
import javax.inject.Inject

class MoviesRepository @Inject constructor(

    private val remoteDataSource: MoviesRemoteDataSource

){

    fun getMovies(){

        return remoteDataSource.getMovies()
    }

}