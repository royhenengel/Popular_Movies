package com.example.popularmovies.di.module

import com.example.popularmovies.data.source.remote.MoviesRemoteDataSource
import com.example.popularmovies.data.source.remote.MoviesRemoteDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    @Binds
    abstract fun bindMoviesRemoteDataSource(moviesRemoteDataSourceImpl: MoviesRemoteDataSourceImpl): MoviesRemoteDataSource

}