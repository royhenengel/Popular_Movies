package com.example.popularmovies.di.module

import com.example.popularmovies.ui.view.details.movie.view.MovieDetailsFragment
import com.example.popularmovies.ui.view.details.person.view.PersonDetailsFragment
import com.example.popularmovies.ui.view.main.view.MainMoviesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMainMoviesFragment(): com.example.popularmovies.ui.view.main.view.MainMoviesFragment

    @ContributesAndroidInjector
    abstract fun contributeMovieDetailsFragment(): com.example.popularmovies.ui.view.details.movie.view.MovieDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributePersonDetailsFragment(): com.example.popularmovies.ui.view.details.person.view.PersonDetailsFragment

}

