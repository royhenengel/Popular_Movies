package com.example.popularmovies.di.modules

import com.example.popularmovies.ui.main.views.MainMoviesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMainMoviesFragment(): MainMoviesFragment

}