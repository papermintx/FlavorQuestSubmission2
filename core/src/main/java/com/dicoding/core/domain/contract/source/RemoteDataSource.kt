package com.dicoding.core.domain.contract.source

import com.dicoding.core.data.remote.response.MealsItemDto
import com.dicoding.core.data.remote.response.MealsResponseDto
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    fun searchRecipes(s: String): Flow<Result<MealsResponseDto>>

    fun getInitialMeal(): Flow<Result<List<MealsItemDto?>>>

    fun getRecipeDetail(i: String): Flow<Result<MealsItemDto>>
}
