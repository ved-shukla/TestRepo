package com.example.practicaltest.ui.movie_list

import android.content.res.Configuration
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaltest.BR
import com.example.practicaltest.R
import com.example.practicaltest.databinding.FragmentMovieListBinding
import com.example.practicaltest.network.Status
import com.example.practicaltest.ui.base.BaseFragment
import com.example.practicaltest.utils.CONTENTLISTINGPAGE1
import com.example.practicaltest.utils.CONTENTLISTINGPAGE2
import com.example.practicaltest.utils.CONTENTLISTINGPAGE3
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieList : BaseFragment<FragmentMovieListBinding, MovieListViewModel>(), MovieListNavigator {
    private var pageNumber: Int = 1
    var pastVisiblesItems = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0
    private var loading = true
    lateinit var mLayoutManager: GridLayoutManager
    private val movieListViewModel: MovieListViewModel by viewModels()
    private lateinit var movieListAdapter: MovieListAdapter
    private lateinit var searchListAdapter: SearchListAdapter

    override fun getViewModel(): MovieListViewModel {
        movieListViewModel.setNavigator(this)
        return movieListViewModel
    }

    override fun getBindingVariable(): Int {
        return BR.movieListViewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_movie_list
    }

    override fun setupUI() {
        // reset paging variable
        resetPagingVariable()
        // init the adapter
        initMovieListAdapter()
        // get all data from database
        movieListViewModel.executeGetMovieList()
        // insert page 1 in database
        insertOrFetchMovieList(CONTENTLISTINGPAGE1)
        // add scroll change listener
        addOnScrollListener()
        // text change listener for search
        searchListener()
    }

    private fun searchListener() {
        viewDataBinding!!.edtSearch.doAfterTextChanged {
            if (it!!.isEmpty()) {
                viewDataBinding!!.cvSearchHint!!.visibility = View.GONE
                movieListAdapter.filter(it.toString())
                viewDataBinding!!.imgCancel.visibility = View.GONE
            } else {
                viewDataBinding!!.imgCancel.visibility = View.VISIBLE
                if (it.length >= 3) {
                    movieListAdapter.filter(it.toString())
                }
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // change span count of the gridview according to the orientation
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mLayoutManager.spanCount = 7
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mLayoutManager.spanCount = 3
        }
    }

    private fun resetPagingVariable() {
        pageNumber = 1
        pastVisiblesItems = 0
        visibleItemCount = 0
        totalItemCount = 0
    }

    private fun insertOrFetchMovieList(pageNumber: String) {
        lifecycleScope.launch {
            movieListViewModel.insertMovieList(pageNumber)
        }
    }

    private fun addOnScrollListener() {
        viewDataBinding!!.rvMovieList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    visibleItemCount = mLayoutManager.childCount
                    totalItemCount = mLayoutManager.itemCount
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition()
                    if (loading && pageNumber < 3) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false
                            pageNumber += pageNumber
                            insertOrFetchMovieList(if (pageNumber == 2) CONTENTLISTINGPAGE2 else CONTENTLISTINGPAGE3)
                            loading = true
                        }
                    }
                }
            }
        })

    }

    private fun initMovieListAdapter() {
        movieListAdapter = MovieListAdapter {
            // set search hint adapter
            val hintList = it.distinctBy { obj -> obj.name }.take(3)
            if (hintList.isNotEmpty() && viewDataBinding!!.edtSearch.text.isNotEmpty()) {
                viewDataBinding!!.cvSearchHint!!.visibility = View.VISIBLE
            } else {
                viewDataBinding!!.cvSearchHint!!.visibility = View.GONE
            }
            searchListAdapter.submitList(hintList)
        }
        mLayoutManager = GridLayoutManager(requireContext(), 3)
        viewDataBinding!!.rvMovieList.layoutManager = mLayoutManager
        viewDataBinding!!.rvMovieList.adapter = movieListAdapter

        searchListAdapter = SearchListAdapter {
            viewDataBinding!!.edtSearch.setText(it.name)
            viewDataBinding!!.cvSearchHint!!.visibility = View.GONE
        }
        viewDataBinding!!.rvSearchHint!!.adapter = searchListAdapter
    }

    override fun setupObserver() {
        movieListViewModel.liveDataGetMovieList.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {
                    movieListAdapter.currentListCopyForSearch =
                        it.data?.toMutableList() ?: mutableListOf()
                    movieListAdapter.submitList(it.data)
                }
                Status.ERROR -> {

                }
            }
        }
    }

    override fun onCancelClicked() {
        viewDataBinding!!.edtSearch.setText("")
    }

    override fun onSearchClicked() {

    }

    override fun onBackClicked() {
        requireActivity().finish()
    }

    override fun onSearchHeaderClicked() {
        viewDataBinding!!.carSearch.visibility =
            if (viewDataBinding!!.carSearch.visibility == View.VISIBLE) View.GONE else View.VISIBLE
    }
}