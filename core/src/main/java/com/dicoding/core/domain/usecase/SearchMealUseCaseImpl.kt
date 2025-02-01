package com.dicoding.core.domain.usecase

import com.dicoding.core.common.State
import com.dicoding.core.data.Resource
import com.dicoding.core.domain.contract.repository.RecipeRepository
import com.dicoding.core.domain.contract.usecase.SearchMealUseCase
import com.dicoding.core.domain.model.Meal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class SearchMealUseCaseImpl(
    private val repository: RecipeRepository
) : SearchMealUseCase {

    override operator fun invoke(query: String): Flow<State<List<Meal>>> = flow {
        repository.searchRecipes(query)
            .collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val data = resource.data.orEmpty().filterNotNull()
                        if (data.isNotEmpty()) {
                            emit(State.Success(data))
                        } else {
                            emit(State.Error("the recipe you are looking for does not exist"))
                        }
                    }
                    is Resource.Loading -> emit(State.Loading)
                    is Resource.Error -> {
                        emit(State.Error(resource.message ?: "An unknown error occurred"))
                    }
                }
            }
    }.flowOn(Dispatchers.IO)
}
