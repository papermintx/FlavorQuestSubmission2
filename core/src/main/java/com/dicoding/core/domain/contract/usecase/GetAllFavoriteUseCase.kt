package com.dicoding.core.domain.contract.usecase

import com.dicoding.core.common.State
import com.dicoding.core.domain.model.Meal
import kotlinx.coroutines.flow.Flow

interface GetAllFavoriteUseCase {
    suspend operator fun invoke(): Flow<State<List<Meal?>>>

}