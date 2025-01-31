package com.dicoding.core.data.repository

import android.util.Log
import com.dicoding.core.data.remote.mapper.toDomain
import com.dicoding.core.data.remote.mapper.toEntity
import com.dicoding.core.data.remote.mapper.toFavoriteEntity
import com.dicoding.core.data.remote.mapper.toMealDetail
import com.dicoding.core.domain.contract.repository.RecipeRepository
import com.dicoding.core.domain.contract.source.LocalDataSource
import com.dicoding.core.domain.contract.source.RemoteDataSource
import com.dicoding.core.domain.model.Meal
import com.dicoding.core.domain.model.MealDetail
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

class RecipeRepositoryImpl(
    private val remoteDataSourceImpl: RemoteDataSource,
    private val localDataSource: LocalDataSource
): RecipeRepository {

    override fun searchRecipes(s: String): Flow<List<Meal?>> =
        remoteDataSourceImpl.searchRecipes(s)
            .map { response ->
                if (response.isSuccess) {
                    val mealsResponseDto = response.getOrNull()
                    if (mealsResponseDto != null) {
                        mealsResponseDto.meals?.map { it?.toDomain() } ?: emptyList()
                    } else {
                        Log.d(TAG, "searchRecipes: Data is empty")
                        throw IllegalStateException("Data is empty")
                    }
                } else {
                    Log.e(TAG, "searchRecipes: ${response.exceptionOrNull()}")
                    throw response.exceptionOrNull() ?: Exception("An error occurred. Please try again.")
                }
            }
            .catch { e ->
                when (e) {
                    is TimeoutCancellationException -> {
                        Log.e(TAG, "searchRecipes: Request timed out", e)
                        throw TimeoutException("Request timed out. Please try again.")
                    }
                    is UnknownHostException -> {
                        Log.e(TAG, "searchRecipes: No internet connection", e)
                        throw IOException("No internet connection. Please check your connection.")
                    }
                    is IOException -> {
                        Log.e(TAG, "searchRecipes: IOException", e)
                        throw IOException("An error occurred. Please try again.")
                    }
                    else -> {
                        Log.e(TAG, "searchRecipes: Unexpected error", e)
                        throw Exception(e.message)
                    }
                }
            }

    override suspend fun insertMealToDatabase(meals: List<Meal>) {
        val mealsEntity = meals.map {
            it.toEntity()
        }
        localDataSource.insertMeals(mealsEntity)
    }

    override suspend fun getInitialMeal(): List<Meal?> {
        try {
            val response = remoteDataSourceImpl.getInitialMeal()
            if (response.isSuccess) {
                val mealsResponseDto = response.getOrNull()

                if (mealsResponseDto != null) {
                    return mealsResponseDto.map { data ->
                        data?.toDomain()
                    }
                } else {
                    Log.d(TAG, "getInitialMeal: Data is empty")
                    throw IllegalStateException("Data is empty")
                }
            } else {
                Log.e(TAG, "getInitialMeal: ${response.exceptionOrNull()}")
                throw response.exceptionOrNull() ?: throw Exception("An error occurred. Please try again.")
            }
        } catch (e: TimeoutCancellationException) {
            Log.e(TAG, "getInitialMeal: Request timed out", e)
            throw TimeoutException("Request timed out. Please try again.")
        } catch (e: UnknownHostException){
            Log.e(TAG, "getInitialMeal: No internet connection", e)
            throw IOException("No internet connection. Please check your connection.")
        } catch (e: IOException) {
            Log.e(TAG, "getInitialMeal: IOException", e)
            throw IOException("An error occurred. Please try again.")
        } catch (e: Exception) {
            Log.e(TAG, "getInitialMeal: Unexpected error", e)
            throw Exception(e.message)
        }
    }

    override fun getAllFavoriteMeal(): Flow<List<Meal?>> {
        return localDataSource.getAllMealsFavorite().map { mealFavoriteEntities ->
            mealFavoriteEntities.map { mealFavoriteEntity ->
                mealFavoriteEntity?.toDomain()
            }
        }
    }

    override fun getAllInitialMeal(): Flow<List<Meal?>> {
        return localDataSource.getAllMeals().map { mealEntities ->
            if (mealEntities.isEmpty()) {
                emptyList()
            } else{
                mealEntities.map { mealEntity ->
                    mealEntity?.toDomain()
                }
            }

        }
    }

    override suspend fun insertFavoriteMealToDatabase(meals: Meal) {
        localDataSource.insertMealsFavorite(meals.toFavoriteEntity())
    }

    override suspend fun getRecipeDetail(i: String): Flow<MealDetail?> = flow {
        try {
            remoteDataSourceImpl.getRecipeDetail(i).collect { response ->
                if (response.isSuccess) {
                    val mealDetailDto = response.getOrNull()

                    if (mealDetailDto != null) {
                        emit(mealDetailDto.toMealDetail())
                    } else {
                        Log.d(TAG, "getRecipeDetail: Data is empty")
                        throw IllegalStateException("Data is empty")
                    }
                } else {
                    Log.e(TAG, "getRecipeDetail: ${response.exceptionOrNull()}")
                    throw response.exceptionOrNull() ?: throw Exception("An error occurred. Please try again.")
                }
            }


        } catch (e: TimeoutCancellationException) {
            Log.e(TAG, "getRecipeDetail: Request timed out", e)
            throw TimeoutException("Request timed out. Please try again.")
        } catch (e: UnknownHostException){
            Log.e(TAG, "getRecipeDetail: No internet connection", e)
            throw IOException("No internet connection. Please check your connection.")
        } catch (e: IOException) {
            Log.e(TAG, "getRecipeDetail: IOException", e)
            throw IOException("An error occurred. Please try again.")
        } catch (e: Exception) {
            Log.e(TAG, "getRecipeDetail: Unexpected error", e)
            throw Exception(e.message)
        }
    }

    override suspend fun deleteMealFavorite(meal: Meal) {
        try {
            localDataSource.deleteMealFavorite(meal.toFavoriteEntity())
        } catch (e: Exception) {
            Log.e(TAG, "deleteMeal: Unexpected error", e)
            throw Exception(e.message)
        }
    }


    companion object{
        const val TAG = "RecipeRepositoryImpl"
    }
}