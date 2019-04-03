package com.example.popularmovies.di.modules

import com.example.popularmovies.data.main.source.remote.MoviesRemoteDataSource
import com.example.popularmovies.data.main.source.remote.MoviesRemoteDataSourceCoroutinesImpl
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    @Binds
    abstract fun bindRemoteMoviesDataSource(moviesRemoteDataSourceCoroutinesImpl: MoviesRemoteDataSourceCoroutinesImpl): MoviesRemoteDataSource

}