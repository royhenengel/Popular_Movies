package com.example.popularmovies.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.popularmovies.di.ViewModelKey
import com.example.popularmovies.ui.main.viewmodels.MainMoviesFragmentViewModel
import com.example.popularmovies.viewmodel.MoviesViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule{

    @Binds
    @IntoMap
    @ViewModelKey(MainMoviesFragmentViewModel::class)
    abstract fun bindMainMoviesFragmentViewModel(viewModel: MainMoviesFragmentViewModel) : ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: MoviesViewModelFactory) : ViewModelProvider.Factory

}