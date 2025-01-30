package com.dicoding.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy
import com.dicoding.core.data.local.entity.MealEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meals: List<MealEntity>)

    @Query("SELECT * FROM meals")
    fun getAllMeals(): Flow<List<MealEntity?>>

    @Query("DELETE FROM meals")
    suspend fun deleteAllMeals()

}
