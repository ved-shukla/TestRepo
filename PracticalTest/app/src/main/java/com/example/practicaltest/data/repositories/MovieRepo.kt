package com.example.practicaltest.data.repositories

import android.content.Context
import android.util.Log
import com.example.practicaltest.data.local.MovieDao
import com.example.practicaltest.data.local.performGetOperation
import com.example.practicaltest.utils.CONTENTLISTINGPAGE1
import com.example.practicaltest.utils.readAndParseJson
import javax.inject.Inject

class MovieRepo @Inject constructor(
    private val applicationContext: Context,
    private val movieDao: MovieDao
) {

    fun readMovieList() = performGetOperation(
        databaseQuery = {
            movieDao.getAllMovie()
        }, saveCallResult = {

        }
    )

    suspend fun insertMovieByPage(jsonFileName: String) {
        if(jsonFileName==CONTENTLISTINGPAGE1){
            movieDao.deleteAllMovie()
        }
        val movieModel = readAndParseJson(jsonFileName, applicationContext)
        movieDao.insertAllMovie(movieModel?.page?.content?.content_items ?: arrayListOf())
    }

    suspend fun clearMovieTable(){
        movieDao.deleteAllMovie()
    }
}