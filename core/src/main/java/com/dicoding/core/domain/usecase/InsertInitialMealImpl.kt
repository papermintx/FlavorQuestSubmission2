package com.dicoding.core.domain.usecase

import android.util.Log
import com.dicoding.core.domain.contract.repository.RecipeRepository
import com.dicoding.core.domain.contract.usecase.InsertInitialMeal

class InsertInitialMealImpl(
    private val repository: RecipeRepository
) : InsertInitialMeal {
    override suspend fun invoke() {
        try {
            repository.searchRecipes("chicken").collect { data ->
                if (data.isNotEmpty()) {
                    val nonNullData = data.filterNotNull()
                    if (nonNullData.isNotEmpty()) {
                        repository.insertMealToDatabase(nonNullData)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("InsertInitialMealImpl", "${e.message}")
        }
    }
}
