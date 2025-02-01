package com.dicoding.core.data.local.room.utils

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import androidx.room.TypeConverter

class Converter {

    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val listType = Types.newParameterizedType(List::class.java, String::class.java)
    private val listAdapter = moshi.adapter<List<String>>(listType)

    @TypeConverter
    fun fromList(list: List<String>?): String {
        return listAdapter.toJson(list) ?: "[]"
    }

    @TypeConverter
    fun toList(json: String?): List<String> {
        return json?.let { listAdapter.fromJson(it) } ?: emptyList()
    }
}