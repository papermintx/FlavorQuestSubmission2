package com.dicoding.core.domain.usecase

import com.dicoding.core.common.State
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

    override operator fun invoke(query: String): Flow<State<List<Meal>>> {
        return flow {
            emit(State.Loading)
            try {
                repository.searchRecipes(query).collect { data ->
                    if (data.isNotEmpty()) {
                        val nonNullData = data.filterNotNull()
                        if (nonNullData.isNotEmpty()) {
                            emit(State.Success(nonNullData))
                        } else {
                            emit(State.Error("Data is empty after filtering"))
                        }
                    } else {
                        emit(State.Error("Data is null or empty"))
                    }
                }
            } catch (e: Exception) {
                emit(State.Error(e.message ?: "An unknown error occurred"))
            }
        }.flowOn(Dispatchers.IO)
    }
}
