package com.example.popularmovies.di.modules

import com.example.popularmovies.data.main.source.remote.MoviesRemoteDataSource
import com.example.popularmovies.data.main.source.remote.MoviesRemoteDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    @Binds
    abstract fun bindRemoteMoviesDataSource(moviesRemoteDataSourceImpl: MoviesRemoteDataSourceImpl): MoviesRemoteDataSource

}