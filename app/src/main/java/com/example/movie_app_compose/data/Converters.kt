package com.example.movie_app_compose.data

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.movie_app_compose.model.Genre
import com.example.movie_app_compose.model.KnownFor
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    var gson = Gson()

    @TypeConverter
    fun genretoString(list: List<Genre>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun stringToGenre(genre: String?): List<Genre> {
        if (genre == null) {
            return emptyList()
        }
        var listType = object : TypeToken<List<Genre>>() {}.type

        return gson.fromJson(genre, listType)
    }

    @TypeConverter
    fun knowntoString(list: List<KnownFor>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun stringToKnown(data: String?): List<KnownFor> {
        if (data == null) {
            return emptyList()
        }
        var listType = object : TypeToken<List<KnownFor>>() {}.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun inttoString(list: List<Int>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun stringToInt(data: String?): List<Int> {
        if (data == null) {
            return emptyList()
        }
        var listType = object : TypeToken<List<Int>>() {}.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun stringtoString(list: List<String>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun stringToString(data: String?): List<String> {
        if (data == null) {
            return emptyList()
        }
        var listType = object : TypeToken<List<String>>() {}.type

        return gson.fromJson(data, listType)
    }
}