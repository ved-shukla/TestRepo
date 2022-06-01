package com.example.practicaltest.ui.movie_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.example.practicaltest.data.repositories.MovieRepo
import com.example.practicaltest.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val movieRepo: MovieRepo) :
    BaseViewModel<MovieListNavigator>() {
    private val _tagGetMovieList = MutableLiveData<Boolean>()
    fun executeGetMovieList() {
        _tagGetMovieList.value = true
    }

    val liveDataGetMovieList = _tagGetMovieList.switchMap {
        getMovieListByPaging()
    }

    private fun getMovieListByPaging() = movieRepo.readMovieList()

    suspend fun insertMovieList(fileName: String) = movieRepo.insertMovieByPage(fileName)
}