package com.dicoding.core.domain.usecase

import android.util.Log
import com.dicoding.core.domain.contract.repository.RecipeRepository
import com.dicoding.core.domain.contract.usecase.InsertInitialMealUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InsertInitialMealUseCaseImpl(
    private val repository: RecipeRepository
) : InsertInitialMealUseCase {
    override suspend fun invoke() {
        withContext(Dispatchers.IO){
            try {
                val response = repository.getInitialMeal()
                if (response.isNotEmpty()) {
                    Log.d(TAG, "invoke: response is not empty ${response.size}")
                    repository.insertMealToDatabase(response.filterNotNull())
                } else {
                    Log.d(TAG, "invoke: response is empty")
                }
            } catch (e: CancellationException) {
                Log.e("InsertInitialMealImpl", "${e.message}")
            } catch (e: Exception) {
                Log.e("InsertInitialMealImpl", "${e.message}")
            }
        }
    }

    companion object {
        private const val TAG = "InsertInitialMealImpl"
    }
}