package com.example.popularmovies.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.popularmovies.di.ViewModelKey
import com.example.popularmovies.ui.details.movie.viewmodel.MovieDetailsFragmentViewModel
import com.example.popularmovies.ui.details.person.viewmodel.PersonDetailsFragmentViewModel
import com.example.popularmovies.ui.main.viewmodel.MainMoviesFragmentViewModel
import com.example.popularmovies.viewmodel.MoviesViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: MoviesViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainMoviesFragmentViewModel::class)
    internal abstract fun bindMainMoviesFragmentViewModel(viewModel: MainMoviesFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsFragmentViewModel::class)
    internal abstract fun bindMovieDetailsFragmentViewModel(viewModel: MovieDetailsFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PersonDetailsFragmentViewModel::class)
    internal abstract fun bindPersonDetailsFragmentViewModel(viewModel: PersonDetailsFragmentViewModel): ViewModel

}