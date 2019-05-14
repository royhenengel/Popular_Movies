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
    }

    private fun observe() {

        fragmentViewModel.genresListLiveData.observe(this, Observer { handleGenresListData(it) })
        fragmentViewModel.categoriesLiveData.observe(this, Observer { handleCategoriesListData(it) })
    }

    private fun handleGenresListData(genres: FilterDialogFragmentViewModel.Genres) {

        val dataAdapter = ArrayAdapter<String>(context!!, android.R.layout.simple_spinner_item, genres.genresList)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        genresSpinner.adapter = dataAdapter
        genresSpinner.setSelection(genres.selectedGenre)

        genresSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                onGenreSelected(position)
            }

        }
    }

    private fun onGenreSelected(position: Int) {

        fragmentViewModel.onGenresSelected(position)
    }

    private fun handleCategoriesListData(categories: FilterDialogFragmentViewModel.Categories) {

        val dataAdapter = ArrayAdapter<String>(context!!, android.R.layout.simple_spinner_item, categories.categoriesList)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        categoriesSpinner.adapter = dataAdapter
        categoriesSpinner.setSelection(categories.selectedCategory)

        categoriesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                onCategorySelected(position)
            }

        }
    }

    private fun onCategorySelected(position: Int) {

        fragmentViewModel.onCategorySelected(position)
    }

}
