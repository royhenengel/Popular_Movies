package com.example.popularmovies.ui.main.view

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.popularmovies.R
import com.example.popularmovies.data.main.entity.MovieEntity
import com.example.popularmovies.di.Injectable
import com.example.popularmovies.ui.main.entity.MovieUiEntity
import com.example.popularmovies.ui.main.entity.mapper.MovieEntityToUiEntityMapper
import com.example.popularmovies.ui.main.viewmodel.MainMoviesFragmentViewModel
import javax.inject.Inject

class MainMoviesFragment : Fragment(), Injectable, MovieViewHolder.MovieClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var movieEntityToUiEntityMapper: MovieEntityToUiEntityMapper

    private lateinit var fragmentViewModel: MainMoviesFragmentViewModel

    private lateinit var moviesRv: RecyclerView
    private lateinit var moviesAdapter: MainMoviesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_main_movies, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main_movies, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId){

            R.id.action_filter_list -> {
                Toast.makeText(context, "Filter list clicked -> TBI", Toast.LENGTH_LONG).show()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fragmentViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainMoviesFragmentViewModel::class.java)

        observe()
    }

    override fun onMovieClicked(uiEntity: MovieUiEntity) {

        fragmentViewModel.onMovieClicked(uiEntity)
    }

    private fun initViews(view: View) {

        moviesRv = view.findViewById<RecyclerView>(R.id.fragment_main_movies_rv).apply {

            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)

            moviesAdapter = MainMoviesAdapter(movieEntityToUiEntityMapper, this@MainMoviesFragment)
            adapter = moviesAdapter
        }
    }

    private fun observe() {

        fragmentViewModel.moviesLiveData.observe(this, Observer { moviesAdapter.submitList(it) })
        fragmentViewModel.onMovieClickedLiveEvent.observe(this, Observer { handleMovieClickedEvent(it) })
    }

    private fun handleMovieClickedEvent(movieEntity: MovieEntity) {

        val action = MainMoviesFragmentDirections.actionDestMainToDestMovieDetails(movieEntity.id)
        findNavController().navigate(action)
    }

}
