package com.example.androidschool

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.androidschool.model.dto.Country
import com.example.androidschool.model.dto.Genre
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

@ProvidedTypeConverter
class TypeConverter {
    private val gson: Gson = Gson()

    @TypeConverter
    fun stringToGenres(data: String?): List<Genre?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object : TypeToken<List<Genre?>?>() {}.type
        return gson.fromJson<List<Genre?>>(data, listType)
    }

    @TypeConverter
    fun stringToCountries(data: String?): List<Country?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object : TypeToken<List<Country?>?>() {}.type
        return gson.fromJson<List<Country?>>(data, listType)
    }

    @TypeConverter
    fun objectsListToJSONString(someObjects: List<Genre?>?): String? {
        return gson.toJson(someObjects)
    }
    @TypeConverter
    fun objectsListToJSONString1(someObjects: List<Country?>?): String? {
        return gson.toJson(someObjects)
    }
}