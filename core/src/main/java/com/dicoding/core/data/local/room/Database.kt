package com.dicoding.core.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dicoding.core.data.local.room.dao.MealDao
import com.dicoding.core.data.local.room.dao.MealDetailDao
import com.dicoding.core.data.local.room.dao.MealFavoriteDao
import com.dicoding.core.data.local.room.entity.MealDetailEntity
import com.dicoding.core.data.local.room.entity.MealEntity
import com.dicoding.core.data.local.room.entity.MealFavoriteEntity
import com.dicoding.core.data.local.room.utils.Converter

@Database(
    entities = [
        MealEntity::class,
        MealFavoriteEntity::class,
        MealDetailEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class Database: RoomDatabase() {
    abstract fun mealDao(): MealDao
    abstract fun mealFavoriteDao(): MealFavoriteDao
    abstract fun mealDetailDao(): MealDetailDao
}