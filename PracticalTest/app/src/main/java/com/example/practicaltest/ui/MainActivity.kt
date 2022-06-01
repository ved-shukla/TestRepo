package com.example.practicaltest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.practicaltest.R
import com.example.practicaltest.data.local.MovieDao
import com.example.practicaltest.data.local.MovieDatabase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    @Inject
    lateinit var movieDatabase: MovieDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.nav_host_fragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        clearDataBase()
    }

    private fun clearDataBase() {
        lifecycleScope.launch {
            movieDatabase.movieDao().deleteAllMovie()
        }
    }
}