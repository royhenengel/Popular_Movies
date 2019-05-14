package com.example.popularmovies.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popularmovies.data.MoviesRepository
import com.example.popularmovies.data.source.remote.MoviesRemoteDataSource
import javax.inject.Inject

class FilterDialogFragmentViewModel @Inject constructor(

        private val repository: MoviesRepository

) : ViewModel() {

    companion object {
        private const val DEFAULT_SELECT_LIST_POSITION = 0;
    }

    val genresListLiveData = MutableLiveData<Genres>()
    val categoriesLiveData = MutableLiveData<Categories>()
    val sortByValuesLiveData = MutableLiveData<SortByValue>()

    fun start() {

        genresListLiveData.value = initGenres()
        categoriesLiveData.value = initCategories()
        sortByValuesLiveData.value = initSortByValues()
    }

    fun onGenresSelected(position: Int) {

        genresListLiveData.value?.selectedGenre = position
    }

    fun onCategorySelected(position: Int) {

        categoriesLiveData.value?.selectedCategory = position
    }

    fun onSortByValueSelected(position: Int) {

        sortByValuesLiveData.value?.selectValue = position
    }

    private fun initGenres(): Genres {

        val genresNames = mutableListOf<String>()
        val movieGenres = repository.getMovieGenres()

        for (genre in movieGenres) {
            genresNames.add(genre.name)
        }

        return Genres(genresNames, DEFAULT_SELECT_LIST_POSITION)
    }

    private fun initCategories(): Categories {

        val categoryNames = mutableListOf<String>()
        val categories = MoviesRemoteDataSource.CATEGORY.values()

        for (category in categories) {
            categoryNames.add(category.description)
        }

        val categoryPopularPosition = getPopularCategoryPosition(categories)

        return Categories(categoryNames, categoryPopularPosition)
    }

    private fun initSortByValues(): SortByValue? {

        val values = mutableListOf<String>()
        val sortByValues = MoviesRemoteDataSource.SORTBY.values()

        for (value in sortByValues) {
            values.add(value.literal)
        }

        return SortByValue(values, DEFAULT_SELECT_LIST_POSITION)
    }

    private fun getPopularCategoryPosition(categories: Array<MoviesRemoteDataSource.CATEGORY>): Int {

        for ((index, category) in categories.withIndex()) {
            if (category == MoviesRemoteDataSource.CATEGORY.POPULAR) {
                return index
            }
        }

        return DEFAULT_SELECT_LIST_POSITION
    }

    data class Genres(

            val genresList: List<String>,

            var selectedGenre: Int

    )

    data class Categories(

            val categoriesList: List<String>,

            var selectedCategory: Int

    )

    data class SortByValue(

            val sortByList: List<String>,

            var selectValue: Int

    )

}
