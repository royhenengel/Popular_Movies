package com.example.popularmovies.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popularmovies.data.MoviesRepository
import javax.inject.Inject

class FilterDialogFragmentViewModel @Inject constructor(

    private val repository: MoviesRepository

) : ViewModel() {

    val genresListLiveData = MutableLiveData<List<String>>()

    fun start() {

        genresListLiveData.value = initCategories()

    }

    private fun initCategories(): List<String> {

        val genresNames = mutableListOf<String>()
        val movieGenres = repository.getMovieGenres()

        for (genre in movieGenres) {
            genresNames.add(genre.name)
        }

        return genresNames
    }
}
