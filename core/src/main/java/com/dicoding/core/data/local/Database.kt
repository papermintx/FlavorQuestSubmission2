package com.dicoding.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.core.data.local.dao.MealDao
import com.dicoding.core.data.local.dao.MealFavoriteDao
import com.dicoding.core.data.local.entity.MealEntity
import com.dicoding.core.data.local.entity.MealFavoriteEntity

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