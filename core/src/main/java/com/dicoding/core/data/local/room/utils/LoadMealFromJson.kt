package com.dicoding.core.data.local.room.utils

import android.content.Context
import com.dicoding.core.data.local.room.entity.MealEntity
import com.dicoding.core.data.remote.mapper.toDomain
import com.dicoding.core.data.remote.mapper.toEntity
import com.dicoding.core.data.remote.response.MealsResponseDto
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

fun loadMealsFromJson(context: Context): List<MealEntity> {
    val jsonString = context.assets.open("data.json").bufferedReader().use { it.readText() }

    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val adapter = moshi.adapter(MealsResponseDto::class.java)
    val response = adapter.fromJson(jsonString)

    return response?.meals?.filterNotNull()?.map { it.toDomain().toEntity() } ?: emptyList()
}