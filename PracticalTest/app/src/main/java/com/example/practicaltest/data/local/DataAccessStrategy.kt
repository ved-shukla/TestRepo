package com.example.practicaltest.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.practicaltest.network.Resource
import kotlinx.coroutines.Dispatchers

fun <T> performGetOperation(
    databaseQuery: () -> LiveData<T>,
    saveCallResult: suspend (LiveData<Resource<T>>) -> Unit
): LiveData<Resource<T>> = liveData(
    Dispatchers.IO
) {
    val source = databaseQuery.invoke().map {
        Resource.success(it)
    }
    emit(Resource.loading(null))
    emitSource(source)
    saveCallResult(source)
}