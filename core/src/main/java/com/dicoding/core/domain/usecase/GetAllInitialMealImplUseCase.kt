package com.dicoding.core.domain.usecase

import com.dicoding.core.common.State
import com.dicoding.core.domain.contract.repository.RecipeRepository
import com.dicoding.core.domain.contract.usecase.GetAllMealInitialUseCase
import com.dicoding.core.domain.model.Meal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetAllInitialMealImplUseCase (
    private val repository: RecipeRepository
): GetAllMealInitialUseCase {
    override suspend operator fun invoke() : Flow<State<List<Meal?>>> =flow{
        emit(State.Loading)
        try {
            repository.getAllInitialMeal().collect { data ->
                if (data.isEmpty()) {
                    emit(State.Empty)
                }
                else {
                    emit(State.Success(data))
                }
            }
        } catch (e: Exception) {
            emit(State.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)
}