package com.example.popularmovies.di.module

import com.example.popularmovies.ui.details.movie.view.MovieDetailsFragment
import com.example.popularmovies.ui.details.person.view.PersonDetailsFragment
import com.example.popularmovies.ui.main.view.FilterDialogFragment
import com.example.popularmovies.ui.main.view.MainMoviesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMainMoviesFragment(): MainMoviesFragment

    @ContributesAndroidInjector
    abstract fun contributeMovieDetailsFragment(): MovieDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributePersonDetailsFragment(): PersonDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeFilterDialogFragment(): FilterDialogFragment

}

