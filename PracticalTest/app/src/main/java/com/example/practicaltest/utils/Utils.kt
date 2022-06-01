package com.example.practicaltest.utils

import android.content.Context
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.practicaltest.R
import com.example.practicaltest.data.model.MovieModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import java.lang.reflect.Field
import java.lang.reflect.Type

const val CONTENTLISTINGPAGE1 = "CONTENTLISTINGPAGE-PAGE1.json"
const val CONTENTLISTINGPAGE2 = "CONTENTLISTINGPAGE-PAGE2.json"
const val CONTENTLISTINGPAGE3 = "CONTENTLISTINGPAGE-PAGE3.json"

fun readAndParseJson(jsonFileName: String, context: Context): MovieModel? {
    val typeToken: Type = object : TypeToken<MovieModel?>() {}.type
    return Gson().fromJson(
        getJSONFromAssets(jsonFileName, context),
        typeToken
    )
}

fun getJSONFromAssets(fileName: String, context: Context): String? {
    return try {
        val stream: InputStream =
            context.assets.open(fileName)
        val size = stream.available()
        val buffer = ByteArray(size)
        stream.read(buffer)
        stream.close()
        String(buffer)
    } catch (e: IOException) {
        e.printStackTrace()
        ""
    }
}

fun ImageView.getImageAccordingToName(imageName: String) {
    try {
        val resource = context.resources.getIdentifier(
            imageName.replace(".jpg", ""),
            "drawable",
            context.packageName
        )
        this.setImageResource(
            if (resource == 0) R.drawable.placeholder_for_missing_posters else resource
        )
    } catch (E: Exception) {
        this.setImageResource(
            R.drawable.placeholder_for_missing_posters
        )
    }

}