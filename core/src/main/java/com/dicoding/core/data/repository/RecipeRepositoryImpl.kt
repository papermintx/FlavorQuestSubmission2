package com.dicoding.core.data.repository

import android.util.Log
import com.dicoding.core.data.NetworkBoundResource
import com.dicoding.core.data.Resource
import com.dicoding.core.data.mapper.toDetailEntity
import com.dicoding.core.data.mapper.toDomain
import com.dicoding.core.data.mapper.toEntity
import com.dicoding.core.data.mapper.toFavoriteEntity
import com.dicoding.core.data.remote.response.MealsItemDto
import com.dicoding.core.domain.contract.repository.RecipeRepository
import com.dicoding.core.domain.contract.source.LocalDataSource
import com.dicoding.core.domain.contract.source.RemoteDataSource
import com.dicoding.core.domain.model.Meal
import com.dicoding.core.domain.model.MealDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class RecipeRepositoryImpl(
    private val remoteDataSourceImpl: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : RecipeRepository {
    private var currentQuery: String? = null
    private var lastSuccessfulQuery: String? = null

    override fun searchRecipes(s: String): Flow<Resource<List<Meal?>>> {
        return object : NetworkBoundResource<List<Meal?>, List<MealsItemDto>>() {

            override suspend fun fetchFromNetwork(): Flow<List<MealsItemDto>> {
                Log.d(TAG, "fetchFromNetwork: $s")
                currentQuery = s // Set currentQuery to the new query

                return remoteDataSourceImpl.searchRecipes(s).map { response ->
                    Log.d(TAG, "fetchFromNetwork: response: $response")
                    if (response.isSuccess) {
                        Log.d(TAG, "fetchFromNetwork: response: ${response.getOrNull()}")
                        val data = response.getOrNull()
                        data?.meals?.filterNotNull() ?: emptyList()
                    } else {
                        Log.e(TAG, "fetchFromNetwork: Error fetching data from network", response.exceptionOrNull())
                        throw response.exceptionOrNull() ?: Exception("An error occurred. Please try again.")
                    }
                }
            }

            override suspend fun saveNetworkResult(item: List<MealsItemDto>) {
                try {
                    localDataSource.deleteAllMeals()
                    localDataSource.insertMeals(item.map { it.toDomain().toEntity() })
                    // If save is successful, update lastSuccessfulQuery
                    lastSuccessfulQuery = currentQuery
                    Log.d(TAG, "saveNetworkResult: lastSuccessfulQuery updated to $lastSuccessfulQuery")
                } catch (e: Exception) {
                    Log.e(TAG, "saveNetworkResult: Error saving to DB", e)
                    throw e
                }
            }

            override fun loadFromDb(): Flow<List<Meal?>> {
                return localDataSource.getAllMeals().map { entities ->
                    Log.d(TAG, "loadFromDb: $entities")
                    entities.map { it?.toDomain() }
                }.catch { e ->
                    Log.e(TAG, "loadFromDb: Error loading from DB", e)
                    emit(emptyList())
                }
            }

            override fun shouldFetch(data: List<Meal?>?): Boolean {
                // Fetch if data is null/empty or the query has changed
                val shouldFetch = data.isNullOrEmpty() || s != lastSuccessfulQuery
                Log.d(TAG, "shouldFetch: Data is null or empty = ${data.isNullOrEmpty()}, Query changed = ${s != lastSuccessfulQuery}")
                return shouldFetch
            }
        }.asFlow()
    }

    override suspend fun insertMealToDatabase(meals: List<Meal>) {
        val mealsEntity = meals.map { it.toEntity() }
        localDataSource.insertMeals(mealsEntity)
    }

    override fun getInitialMeal(): Flow<Resource<List<Meal?>>> {
        return object : NetworkBoundResource<List<Meal?>, List<MealsItemDto>>() {
            override suspend fun fetchFromNetwork(): Flow<List<MealsItemDto>> {
                return remoteDataSourceImpl.getInitialMeal().map { response ->
                    Log.d(TAG, "fetchFromNetwork: response: $response")
                    if (response.isSuccess) {
                        val data = response.getOrNull()
                        Log.d(TAG, "fetchFromNetwork: data: $data")
                        data?.filterNotNull() ?: throw IllegalStateException("Data is empty")
                    } else {
                        Log.e(TAG, "fetchFromNetwork: Error fetching data from network", response.exceptionOrNull())
                        throw response.exceptionOrNull() ?: Exception("An error occurred. Please try again.")
                    }
                }
            }

            override suspend fun saveNetworkResult(item: List<MealsItemDto>) {
                try {
                    localDataSource.insertMeals(item.map { it.toDomain().toEntity() })
                } catch (e: Exception) {
                    Log.e(TAG, "saveNetworkResult: Error saving to DB", e)
                    throw e
                }
            }

            override fun loadFromDb(): Flow<List<Meal?>> {
                return localDataSource.getAllMeals().map { entities ->
                    entities.map { it?.toDomain() }
                }.catch {
                    throw it
                }
            }

            override fun shouldFetch(data: List<Meal?>?): Boolean {
                // Fetch only if the database is empty
                val shouldFetch = data.isNullOrEmpty()
                Log.d(TAG, "shouldFetch: Data is null or empty = $shouldFetch")
                return shouldFetch
            }
        }.asFlow()
    }

    override fun getAllFavoriteMeal(): Flow<List<Meal?>> {
        return localDataSource.getAllMealsFavorite().map { mealFavoriteEntities ->
            mealFavoriteEntities.map { it?.toDomain() }
        }
    }

    override suspend fun insertFavoriteMealToDatabase(meals: Meal) {
        localDataSource.insertMealsFavorite(meals.toFavoriteEntity())
    }

    override fun getRecipeDetail(i: String): Flow<Resource<MealDetail?>> {
        return object : NetworkBoundResource<MealDetail?, MealsItemDto>() {

            override suspend fun fetchFromNetwork(): Flow<MealsItemDto> {
                return remoteDataSourceImpl.getRecipeDetail(i).map { response ->
                    if (response.isSuccess) {
                        val data = response.getOrNull()
                        data ?: throw IllegalStateException("Data is empty")
                    } else {
                        throw response.exceptionOrNull() ?: Exception("An error occurred. Please try again.")
                    }
                }
            }

            override suspend fun saveNetworkResult(item: MealsItemDto) {
                try {
                    // Delete old data and save new data to the database
                    localDataSource.deleteAllMealDetails()
                    localDataSource.insertMealDetail(item.toDetailEntity())
                    Log.d(TAG, "saveNetworkResult: Data saved for ID = $i")
                } catch (e: Exception) {
                    Log.e(TAG, "saveNetworkResult: Error saving to DB", e)
                    throw e
                }
            }

            override fun loadFromDb(): Flow<MealDetail?> {
                return flow {
                    // Load data from the database by ID
                    val mealDetail = localDataSource.getMealDetailById(i).first()?.toDomain()
                    emit(mealDetail)
                }.catch { e ->
                    // Handle errors when loading from the database
                    Log.e(TAG, "loadFromDb: Error loading from DB", e)
                    emit(null)
                }
            }

            override fun shouldFetch(data: MealDetail?): Boolean {
                // Fetch only if the database is empty or the ID has changed
                val shouldFetch = data == null || i != data.idMeal
                Log.d(TAG, "shouldFetch: Data is null = ${data == null}, ID changed = ${i != data?.idMeal}")
                return shouldFetch
            }
        }.asFlow()
    }

    override suspend fun deleteMealFavorite(meal: Meal) {
        try {
            localDataSource.deleteMealFavorite(meal.toFavoriteEntity())
        } catch (e: Exception) {
            Log.e(TAG, "deleteMeal: Unexpected error", e)
            throw Exception(e.message)
        }
    }

    companion object {
        const val TAG = "RecipeRepositoryImpl"
    }
}