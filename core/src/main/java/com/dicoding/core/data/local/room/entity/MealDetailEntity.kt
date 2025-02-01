package com.dicoding.core.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "mealsDetail")
data class MealDetailEntity(
    @PrimaryKey val id: String,
    val name: String?,
    val category: String?,
    val area: String?,
    val thumbnail: String?,
    val instructions: String?,
    val youtubeLink: String?,
    val ingredients: List<String>?,
    val measures: List<String>?
)