package com.dicoding.core.domain.contract.source

import com.dicoding.core.data.local.room.entity.MealDetailEntity
import com.dicoding.core.data.local.room.entity.MealEntity
import com.dicoding.core.data.local.room.entity.MealFavoriteEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun insertMeals(meals: List<MealEntity>)

    fun getAllMeals(): Flow<List<MealEntity?>>

    suspend fun deleteAllMeals()

    suspend fun insertMealsFavorite(meals: MealFavoriteEntity)

    fun getAllMealsFavorite(): Flow<List<MealFavoriteEntity?>>

    suspend fun deleteMealFavorite(meal: MealFavoriteEntity)

    suspend fun insertMealDetail(mealDetail: MealDetailEntity)

    fun getMealDetailById(id: String): Flow<MealDetailEntity?>

    suspend fun deleteMealDetailById(id: String)

    suspend fun deleteAllMealDetails()

}
