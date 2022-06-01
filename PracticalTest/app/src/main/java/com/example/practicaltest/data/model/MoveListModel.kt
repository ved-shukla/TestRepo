package com.example.practicaltest.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie_master")
data class MoveListModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 1,
    var name: String,
    @SerializedName("poster-image")
    var poster_image: String
)