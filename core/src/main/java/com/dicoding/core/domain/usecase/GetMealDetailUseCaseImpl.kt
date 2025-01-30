package com.dicoding.core.domain.usecase

import com.dicoding.core.common.State
import com.dicoding.core.domain.contract.repository.RecipeRepository
import com.dicoding.core.domain.contract.usecase.GetMealDetailUseCase
import com.dicoding.core.domain.model.MealDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetMealDetailUseCaseImpl(
    private val repository: RecipeRepository
): GetMealDetailUseCase {
    override suspend operator fun invoke(id: String): Flow<State<MealDetail>> {
        return flow {
            emit(State.Loading)
            try {
                repository.getRecipeDetail(id).collect{ data ->
                    if (data != null) {
                        emit(State.Success(data))
                    } else {
                        emit(State.Error("Data is null"))
                    }
                }
            } catch (e: Exception) {
                emit(State.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)

    }
}