package com.example.popularmovies.di.module

import com.example.popularmovies.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainMoviesActivity(): MainActivity

}