package com.example.popularmovies.di.module

import com.example.popularmovies.data.remote.DetailsRemoteDataSource
import com.example.popularmovies.data.remote.DetailsRemoteDataSourceImpl
import com.example.popularmovies.data.remote.MoviesRemoteDataSource
import com.example.popularmovies.data.remote.MoviesRemoteDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    @Binds
    abstract fun bindDetailsMoviesDataSource(detailsRemoteDataSource: com.example.popularmovies.data.remote.DetailsRemoteDataSourceImpl): com.example.popularmovies.data.remote.DetailsRemoteDataSource

}