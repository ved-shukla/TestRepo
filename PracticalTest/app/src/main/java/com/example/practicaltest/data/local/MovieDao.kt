package com.example.practicaltest.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.practicaltest.data.model.MoveListModel

/**
 * Used to perform insert,update and delete operation
 * */
@Dao
interface MovieDao {

    @Query("SELECT * FROM movie_master")
    fun getAllMovie(): LiveData<List<MoveListModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovie(characters: List<MoveListModel>)

    @Query("DELETE from movie_master")
    suspend fun deleteAllMovie()
}