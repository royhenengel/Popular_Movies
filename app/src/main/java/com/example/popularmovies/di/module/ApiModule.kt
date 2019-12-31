package com.example.popularmovies.di.module

import com.example.popularmovies.remote.api.MoviesService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module (includes = [NetworkModule::class])
object ApiModule {

    @Provides
    @JvmStatic
    fun provideMoviesService(retrofit: Retrofit) : com.example.popularmovies.remote.api.MoviesService {

        return retrofit.create(com.example.popularmovies.remote.api.MoviesService::class.java)
    }

}