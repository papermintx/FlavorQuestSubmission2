package com.dicoding.core.data.remote

import com.dicoding.core.data.remote.response.MealsItemDto
import com.dicoding.core.data.remote.response.MealsResponseDto
import com.dicoding.core.domain.contract.source.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class RemoteDataSourceImpl(
    private val apiService: ApiService
) : RemoteDataSource {

    override fun searchRecipes(s: String): Flow<Result<MealsResponseDto>> = flow {
        try {
            val response = apiService.searchRecipes(s)
            emit(handleResponse(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override fun getInitialMeal(): Flow<Result<List<MealsItemDto?>>> = flow {
        try {
            val response = apiService.searchRecipes("")
            emit(handleResponse(response) { it.meals })
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override fun getRecipeDetail(i: String): Flow<Result<MealsItemDto>> = flow {
        try {
            val response = apiService.getRecipeDetail(i)
            val result = handleResponse(response) { it.meals?.firstOrNull() }
            emit(result)
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    private fun <T, R> handleResponse(
        response: Response<T>,
        mapper: (T) -> R? = { it as? R }
    ): Result<R> {
        return if (response.isSuccessful) {
            val data = response.body()
            if (data != null) {
                val mappedData = mapper(data)
                if (mappedData != null) {
                    Result.success(mappedData)
                } else {
                    Result.failure(Exception("Mapped data is null"))
                }
            } else {
                Result.failure(Exception("Response body is null"))
            }
        } else {
            Result.failure(Exception("Response not successful: ${response.code()}"))
        }
    }

}
