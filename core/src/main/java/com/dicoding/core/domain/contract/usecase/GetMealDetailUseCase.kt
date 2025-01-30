package com.dicoding.core.domain.contract.usecase

import com.dicoding.core.common.State
import com.dicoding.core.domain.model.MealDetail
import kotlinx.coroutines.flow.Flow

interface GetMealDetailUseCase {
    suspend operator fun invoke(id: String): Flow<State<MealDetail>>
}