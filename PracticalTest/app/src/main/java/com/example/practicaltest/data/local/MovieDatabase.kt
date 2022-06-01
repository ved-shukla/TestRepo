package com.example.practicaltest.data.local

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.practicaltest.BuildConfig
import com.example.practicaltest.data.model.MoveListModel
import java.util.concurrent.Executors

@Database(entities = [MoveListModel::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {

        @Volatile
        private var instance: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, MovieDatabase::class.java, "movie-db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .setQueryCallback({ sqlQuery, bindArgs ->
                    /*if (BuildConfig.DEBUG)
                        Log.e("SQL Query:", "$sqlQuery SQL Args: $bindArgs")*/
                }, Executors.newSingleThreadExecutor())
                .build()
    }
}