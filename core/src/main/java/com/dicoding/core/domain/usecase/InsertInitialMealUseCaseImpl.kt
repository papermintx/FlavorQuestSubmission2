package com.dicoding.core.domain.usecase

import android.util.Log
import com.dicoding.core.data.Resource
import com.dicoding.core.domain.contract.repository.RecipeRepository
import com.dicoding.core.domain.contract.usecase.InsertInitialMealUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class InsertInitialMealUseCaseImpl(
    private val repository: RecipeRepository
) : InsertInitialMealUseCase {
    override suspend fun invoke() {
        withContext(Dispatchers.IO) {
            try {
                repository.getInitialMeal().collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            val response = resource.data
                            if (!response.isNullOrEmpty()) {
                                Log.d(TAG, "invoke: response is not empty ${response.size}")
                                repository.insertMealToDatabase(response.filterNotNull())
                            } else {
                                Log.d(TAG, "invoke: response is empty")
                            }
                        }
                        is Resource.Loading -> {
                            Log.d(TAG, "invoke: loading")
                        }
                        is Resource.Error -> {
                            Log.e(TAG, "invoke: ${resource.message}")
                        }
                    }
                }
            } catch (e: CancellationException) {
                Log.e(TAG, "${e.message}")
            } catch (e: Exception) {
                Log.e(TAG, "${e.message}")
            }
        }
    }

    companion object {
        private const val TAG = "InsertInitialMealImpl"
    }
}