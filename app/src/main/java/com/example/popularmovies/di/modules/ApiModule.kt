package com.example.popularmovies.di.modules

import com.example.popularmovies.api.main.MoviesService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module (includes = [NetworkModule::class])
object ApiModule {

    @Provides
    @JvmStatic
    fun provideMoviesService(retrofit: Retrofit) : MoviesService {

        return retrofit.create(MoviesService::class.java)
    }
}