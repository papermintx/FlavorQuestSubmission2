package com.dicoding.core.domain.usecase

import com.dicoding.core.common.State
import com.dicoding.core.domain.contract.repository.RecipeRepository
import com.dicoding.core.domain.contract.usecase.GetAllFavoriteUseCase
import com.dicoding.core.domain.model.Meal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetAllFavoriteUseCaseImpl(
    private val repository: RecipeRepository
): GetAllFavoriteUseCase {
    override suspend fun invoke(): Flow<State<List<Meal?>>> {
        return flow{
            emit(State.Loading)
            try {
                repository.getAllFavoriteMeal().collect{
                    emit(State.Success(it))
                }
            } catch (e: Exception) {
                emit(State.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

}