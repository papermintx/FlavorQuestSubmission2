package com.dicoding.core.data.local

import com.dicoding.core.data.local.dao.MealDao
import com.dicoding.core.data.local.dao.MealFavoriteDao
import com.dicoding.core.data.local.entity.MealEntity
import com.dicoding.core.data.local.entity.MealFavoriteEntity
import com.dicoding.core.domain.contract.source.LocalDataSource
import kotlinx.coroutines.flow.Flow

class LocalDataSourceImpl(
    private val mealDao: MealDao,
    private val mealFavoriteDao: MealFavoriteDao
): LocalDataSource {

    override suspend fun insertMeals(meals: List<MealEntity>) {
        mealDao.insertMeal(meals)
    }

    override fun getAllMeals(): Flow<List<MealEntity?>> {
        return mealDao.getAllMeals()
    }

    override suspend fun deleteAllMeals() {
        mealDao.deleteAllMeals()
    }

    override suspend fun insertMealsFavorite(meals: MealFavoriteEntity) {
        mealFavoriteDao.insertFavorite(meals)
    }

    override fun getAllMealsFavorite(): Flow<List<MealFavoriteEntity?>> {
        return mealFavoriteDao.getAllMealsFavorite()
    }

    override suspend fun deleteMealFavorite(meal: MealFavoriteEntity) {
        mealFavoriteDao.deleteMeal(meal)
    }

}
