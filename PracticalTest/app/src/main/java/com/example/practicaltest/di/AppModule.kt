package com.example.practicaltest.di

import android.content.Context
import com.example.practicaltest.data.local.MovieDao
import com.example.practicaltest.data.local.MovieDatabase
import com.example.practicaltest.data.repositories.MovieRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        MovieDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideMovieDao(db: MovieDatabase) = db.movieDao()

    @Singleton
    @Provides
    fun provideMovieRepo(@ApplicationContext appContext: Context, movieDao: MovieDao) =
        MovieRepo(appContext, movieDao)
}