package com.example.practicaltest.data.model

import com.google.gson.annotations.SerializedName

data class Page(
    @SerializedName("content-items")
    var content: Content,
    var page_num: String,
    var page_size: String,
    var title: String,
    var total_content_items: String
)

data class Content(
    @SerializedName("content")
    var content_items: List<MoveListModel>,
)