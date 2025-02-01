package com.dicoding.core.domain.contract.repository

import com.dicoding.core.data.Resource
import com.dicoding.core.domain.model.Meal
import com.dicoding.core.domain.model.MealDetail
import kotlinx.coroutines.flow.Flow


interface RecipeRepository {

    fun searchRecipes(s: String): Flow<Resource<List<Meal?>>>

    suspend fun insertMealToDatabase(meals: List<Meal>)

    fun getInitialMeal(): Flow<Resource<List<Meal?>>>

    fun getAllFavoriteMeal(): Flow<List<Meal?>>

    suspend fun insertFavoriteMealToDatabase(meals: Meal)

    fun getRecipeDetail(i: String): Flow<Resource<MealDetail?>>

    suspend fun deleteMealFavorite(meal: Meal)
}