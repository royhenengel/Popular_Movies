package com.example.popularmovies.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.popularmovies.R
import com.example.popularmovies.ui.main.viewmodel.FilterDialogFragmentViewModel

class FilterDialogFragment : DialogFragment() {

    companion object {
        fun newInstance() = FilterDialogFragment()
    }

    private val fragmengViewModel: FilterDialogFragmentViewModel by lazy {

        ViewModelProviders.of(this).get(FilterDialogFragmentViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_filter_dialog, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}
