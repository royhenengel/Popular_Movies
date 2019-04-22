package com.example.popularmovies.di.module

import com.example.popularmovies.data.source.remote.DetailsRemoteDataSource
import com.example.popularmovies.data.source.remote.DetailsRemoteDataSourceImpl
import com.example.popularmovies.data.source.remote.MoviesRemoteDataSource
import com.example.popularmovies.data.source.remote.MoviesRemoteDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    @Binds
    abstract fun bindDetailsMoviesDataSource(detailsRemoteDataSource: DetailsRemoteDataSourceImpl): DetailsRemoteDataSource

}