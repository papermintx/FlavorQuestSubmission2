package com.dicoding.core.domain.usecase

import com.dicoding.core.common.State
import com.dicoding.core.data.Resource
import com.dicoding.core.domain.contract.repository.RecipeRepository
import com.dicoding.core.domain.contract.usecase.GetAllMealInitialUseCase
import com.dicoding.core.domain.model.Meal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllInitialMealImplUseCase @Inject constructor(
    private val repository: RecipeRepository
) : GetAllMealInitialUseCase {
    override suspend operator fun invoke(): Flow<State<List<Meal?>>> {
        return repository.getInitialMeal().map { resource ->
            when (resource) {
                is Resource.Success -> {
                    val data = resource.data
                    if (!data.isNullOrEmpty()) {
                        State.Success(data)
                    } else {
                        State.Empty
                    }
                }
                is Resource.Loading -> State.Loading
                is Resource.Error -> State.Error(resource.message ?: "An unknown error occurred")
            }
        }.flowOn(Dispatchers.IO)
    }
}