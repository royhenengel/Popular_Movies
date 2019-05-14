package com.example.popularmovies.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.popularmovies.R
import com.example.popularmovies.di.Injectable
import com.example.popularmovies.ui.main.viewmodel.FilterDialogFragmentViewModel
import javax.inject.Inject


class FilterDialogFragment : DialogFragment(), Injectable, AdapterView.OnItemSelectedListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var genresSpinner: Spinner
    private lateinit var categoriesSpinner: Spinner
    private lateinit var sortBySpinner: Spinner

    companion object {

        fun newInstance() = FilterDialogFragment()
    }

    private val fragmentViewModel: FilterDialogFragmentViewModel by lazy {

        ViewModelProviders.of(this, viewModelFactory).get(FilterDialogFragmentViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_filter_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fragmentViewModel.start()

        observe()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {


    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {


    }

    private fun initViews(view: View) {

        genresSpinner = view.findViewById(R.id.filter_dialog_spinner_genre)
        categoriesSpinner = view.findViewById(R.id.filter_dialog_spinner_category)
        sortBySpinner = view.findViewById(R.id.filter_dialog_spinner_sort_by)


    }

    private fun observe() {

        fragmentViewModel.genresListLiveData.observe(this, Observer { handleGenresData(it) })
        fragmentViewModel.categoriesLiveData.observe(this, Observer { handleCategoriesData(it) })
        fragmentViewModel.sortByValuesLiveData.observe(this, Observer { handleSortByData(it) })
    }

    private fun handleGenresData(genres: FilterDialogFragmentViewModel.Genres) {

        val dataAdapter = ArrayAdapter<String>(context!!, android.R.layout.simple_spinner_item, genres.genresList)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        genresSpinner.adapter = dataAdapter
        genresSpinner.setSelection(genres.selectedGenre)

        genresSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                fragmentViewModel.onGenresSelected(position)
            }

        }
    }

    private fun handleCategoriesData(categories: FilterDialogFragmentViewModel.Categories) {

        val dataAdapter = ArrayAdapter<String>(context!!, android.R.layout.simple_spinner_item, categories.categoriesList)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        categoriesSpinner.adapter = dataAdapter
        categoriesSpinner.setSelection(categories.selectedCategory)

        categoriesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                fragmentViewModel.onCategorySelected(position)
            }

        }
    }

    private fun handleSortByData(values: FilterDialogFragmentViewModel.SortByValue) {

        val dataAdapter = ArrayAdapter<String>(context!!, android.R.layout.simple_spinner_item, values.sortByList)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        sortBySpinner.adapter = dataAdapter
        sortBySpinner.setSelection(values.selectValue)

        sortBySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                fragmentViewModel.onSortByValueSelected(position)
            }
        }
    }

}
