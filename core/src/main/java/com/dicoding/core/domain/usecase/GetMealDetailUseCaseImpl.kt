package com.dicoding.core.domain.usecase

import com.dicoding.core.common.State
import com.dicoding.core.data.Resource
import com.dicoding.core.domain.contract.repository.RecipeRepository
import com.dicoding.core.domain.contract.usecase.GetMealDetailUseCase
import com.dicoding.core.domain.model.MealDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class GetMealDetailUseCaseImpl(
    private val repository: RecipeRepository
): GetMealDetailUseCase {
    override suspend operator fun invoke(id: String): Flow<State<MealDetail>> {
        return repository.getRecipeDetail(id).map { resource ->
            when (resource) {
                is Resource.Success -> {
                    val data = resource.data
                    if (data != null) {
                        State.Success(data)
                    } else {
                        State.Error("Data is null")
                    }
                }
                is Resource.Loading -> State.Loading
                is Resource.Error -> State.Error(resource.message ?: "An error occurred")
            }
        }.flowOn(Dispatchers.IO)
    }
}