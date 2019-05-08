package com.example.popularmovies.di.module

import com.example.popularmovies.ui.details.movie.view.MovieDetailsFragment
import com.example.popularmovies.ui.details.person.view.PersonDetailsFragment
import com.example.popularmovies.ui.main.view.MainMoviesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    internal abstract fun contributeMainMoviesFragment(): MainMoviesFragment

    @ContributesAndroidInjector
    internal abstract fun contributeMovieDetailsFragment(): MovieDetailsFragment

    @ContributesAndroidInjector
    internal abstract fun contributePersonDetailsFragment(): PersonDetailsFragment

}

