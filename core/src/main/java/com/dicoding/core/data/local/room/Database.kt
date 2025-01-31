package com.dicoding.core.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.core.data.local.room.dao.MealDao
import com.dicoding.core.data.local.room.dao.MealFavoriteDao
import com.dicoding.core.data.local.room.entity.MealEntity
import com.dicoding.core.data.local.room.entity.MealFavoriteEntity

@Database(
    entities = [
        MealEntity::class,
        MealFavoriteEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class Database: RoomDatabase() {
    abstract fun mealDao(): MealDao
    abstract fun mealFavoriteDao(): MealFavoriteDao
}