package com.example.popularmovies.di.module

import android.app.Application
import com.example.popularmovies.data.source.local.MoviesDao
import com.example.popularmovies.data.source.local.PopularMoviesDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DataModule {

    @Provides
    @JvmStatic
    @Singleton
    fun providePopularMoviesDao(popularMoviesDb: PopularMoviesDb): MoviesDao {

        return popularMoviesDb.popularMoviesDao()
    }

    @Provides
    @JvmStatic
    @Singleton
    fun providePopularMoviesDb(application: Application): PopularMoviesDb {

        return PopularMoviesDb.create(application.applicationContext)
    }

}