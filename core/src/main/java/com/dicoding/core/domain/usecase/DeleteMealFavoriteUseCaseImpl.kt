package com.dicoding.core.domain.usecase

import com.dicoding.core.domain.contract.repository.RecipeRepository
import com.dicoding.core.domain.contract.usecase.DeleteMealFavoriteUseCase
import com.dicoding.core.domain.model.Meal

class DeleteMealFavoriteUseCaseImpl(
    private val recipeRepository: RecipeRepository
): DeleteMealFavoriteUseCase {
    override suspend fun invoke(meal: Meal) {
        recipeRepository.deleteMealFavorite(meal)
    }
}