package com.dicoding.core.domain.contract.usecase

import com.dicoding.core.domain.model.Meal

interface InsertFavoriteMealUseCase {
    suspend operator fun invoke(meal: Meal)
}