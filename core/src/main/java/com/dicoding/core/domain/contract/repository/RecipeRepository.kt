package com.dicoding.core.domain.contract.repository

import com.dicoding.core.domain.model.Meal
import com.dicoding.core.domain.model.MealDetail
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    fun searchRecipes(s: String) : Flow<List<Meal?>>

    suspend fun insertMealToDatabase(meals: List<Meal>)

    suspend fun getInitialMeal() : List<Meal?>

    fun getAllFavoriteMeal(): Flow<List<Meal?>>

    fun getAllInitialMeal(): Flow<List<Meal?>>

    suspend fun insertFavoriteMealToDatabase(meals: Meal)

    suspend fun getRecipeDetail(i: String) : Flow<MealDetail?>

    suspend fun deleteMealFavorite(meal: Meal)

}