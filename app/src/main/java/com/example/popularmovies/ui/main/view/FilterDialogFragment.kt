package com.example.popularmovies.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.popularmovies.di.Injectable
import com.example.popularmovies.ui.main.viewmodel.FilterDialogFragmentViewModel
import javax.inject.Inject
import android.widget.ArrayAdapter
import android.widget.AdapterView
import com.example.popularmovies.R


class FilterDialogFragment : DialogFragment(), Injectable, AdapterView.OnItemSelectedListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var categorySpinner: Spinner

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

    private fun initViews(view: View) {

        categorySpinner = view.findViewById(R.id.filter_dialog_spinner_genre)
    }

    private fun observe() {

        fragmentViewModel.genresListLiveData.observe(this, Observer { handleGenresListData(it) })
    }

    private fun handleGenresListData(genresList: List<String>) {

        categorySpinner.onItemSelectedListener = this

        val dataAdapter = ArrayAdapter<String>(context!!, android.R.layout.simple_spinner_item, genresList)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        categorySpinner.adapter = dataAdapter
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {


    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {


    }

}
