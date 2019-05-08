package com.example.popularmovies.di.module

import com.example.popularmovies.data.source.remote.DetailsRemoteDataSource
import com.example.popularmovies.data.source.remote.DetailsRemoteDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
abstract class DataBuildersModule {

    @Binds
    internal abstract fun bindDetailsMoviesDataSource(detailsRemoteDataSource: DetailsRemoteDataSourceImpl): DetailsRemoteDataSource

}