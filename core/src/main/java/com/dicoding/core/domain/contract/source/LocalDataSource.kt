package com.dicoding.core.domain.contract.source

import com.dicoding.core.data.local.entity.MealEntity
import com.dicoding.core.data.local.entity.MealFavoriteEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun insertMeals(meals: List<MealEntity>)

    fun getAllMeals(): Flow<List<MealEntity?>>

    suspend fun deleteAllMeals()

    suspend fun insertMealsFavorite(meals: MealFavoriteEntity)

    fun getAllMealsFavorite(): Flow<List<MealFavoriteEntity?>>

    suspend fun deleteMealFavorite(meal: MealFavoriteEntity)

}
