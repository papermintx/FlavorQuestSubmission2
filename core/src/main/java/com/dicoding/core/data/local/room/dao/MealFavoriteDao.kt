package com.dicoding.core.data.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.core.data.local.room.entity.MealFavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MealFavoriteDao {
    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertFavorite(meals: MealFavoriteEntity)

    @Query("SELECT * FROM meals_favorite")
    fun getAllMealsFavorite(): Flow<List<MealFavoriteEntity?>>

    @Query("DELETE FROM meals_favorite")
    suspend fun deleteAllMealsFavorite()

    @Delete
    suspend fun deleteMeal(meal: MealFavoriteEntity)
}