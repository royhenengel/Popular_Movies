package com.example.popularmovies.ui.main.view

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.popularmovies.R
import com.example.popularmovies.data.main.entity.MovieEntity
import com.example.popularmovies.di.Injectable
import com.example.popularmovies.ui.main.entity.MovieUiEntity
import com.example.popularmovies.ui.main.entity.mapper.MovieEntityToUiEntityMapper
import com.example.popularmovies.ui.main.viewmodel.MainMoviesFragmentViewModel
import kotlinx.android.synthetic.main.fragment_main_movies.*
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

        return when (item.itemId) {

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
        fragmentViewModel.start()

        observe()
    }

    override fun onMovieClicked(uiEntity: MovieUiEntity) {

        fragmentViewModel.onMovieClicked(uiEntity)
    }

    private fun initViews(view: View) {

        moviesRv = view.findViewById<RecyclerView>(R.id.fragment_main_movies_rv).apply {

            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        mainMoviesActionTryAgainBtn.setOnClickListener { fragmentViewModel.onTryAgainClicked() }
    }

    private fun observe() {

        fragmentViewModel.moviesLiveData.observe(this, Observer { handleMoviesData(it) })
        fragmentViewModel.onMovieClickedLiveEvent.observe(this, Observer { handleMovieClickedEvent(it) })
        fragmentViewModel.stateLiveData.observe(this, Observer { handleStateData(it) })
    }

    private fun handleMoviesData(pagedList: PagedList<MovieEntity>?) {

        moviesAdapter = MainMoviesAdapter(movieEntityToUiEntityMapper, this@MainMoviesFragment)
        moviesRv.adapter = moviesAdapter
        moviesAdapter.submitList(pagedList)
    }

    private fun handleMovieClickedEvent(movieEntity: MovieEntity) {

        val action = MainMoviesFragmentDirections.actionDestMainToDestMovieDetails(movieEntity.id,
                false)
        findNavController().navigate(action)
    }

    private fun handleStateData(state: MainMoviesFragmentViewModel.STATE) {

        when (state) {

            MainMoviesFragmentViewModel.STATE.LOADING -> showLoading()

            MainMoviesFragmentViewModel.STATE.ERROR -> showError()

            MainMoviesFragmentViewModel.STATE.LOADED -> showLayout()
        }
    }

    /**
     * Show fragment layout only if both movie details has be received and background image has been
     * downloaded and set
     */
    private fun showLayout() {

        mainMoviesLoaderPb.visibility = View.GONE
        mainMoviesErrorViewsGroup.visibility = View.GONE
        moviesRv.visibility = View.VISIBLE
    }

    private fun showLoading() {

        moviesRv.visibility = View.GONE
        mainMoviesErrorViewsGroup.visibility = View.GONE
        mainMoviesLoaderPb.visibility = View.VISIBLE
    }

    private fun showError() {

        mainMoviesErrorTv.text = fragmentViewModel.userErrorMessage

        moviesRv.visibility = View.GONE
        mainMoviesLoaderPb.visibility = View.GONE
        mainMoviesErrorViewsGroup.visibility = View.VISIBLE
    }

}
