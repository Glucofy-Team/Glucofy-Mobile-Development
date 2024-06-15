package com.dicoding2.glucofy.helper

import androidx.room.TypeConverter
import com.google.gson.Gson

class AnyTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromJson(json: String): Any? {
        return gson.fromJson(json, Any::class.java)
    }

    @TypeConverter
    fun toJson(data: Any?): String {
        return gson.toJson(data)
    }
}