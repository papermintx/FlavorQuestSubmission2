package com.dicoding.core.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.core.data.local.room.entity.MealDetailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMealDetail(mealDetail: MealDetailEntity)

    @Query("SELECT * FROM mealsDetail WHERE id = :id")
    fun getMealDetailById(id: String): Flow<MealDetailEntity?>

    @Query("DELETE FROM mealsDetail WHERE id = :id")
    suspend fun deleteMealDetailById(id: String)

    @Query("DELETE FROM mealsDetail")
    suspend fun deleteAllMealDetails()
}