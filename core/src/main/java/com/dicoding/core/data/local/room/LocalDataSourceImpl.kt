package com.dicoding.core.data.local.room

import com.dicoding.core.data.local.room.dao.MealDao
import com.dicoding.core.data.local.room.dao.MealDetailDao
import com.dicoding.core.data.local.room.dao.MealFavoriteDao
import com.dicoding.core.data.local.room.entity.MealDetailEntity
import com.dicoding.core.data.local.room.entity.MealEntity
import com.dicoding.core.data.local.room.entity.MealFavoriteEntity
import com.dicoding.core.domain.contract.source.LocalDataSource
import kotlinx.coroutines.flow.Flow

class LocalDataSourceImpl(
    private val mealDao: MealDao,
    private val mealFavoriteDao: MealFavoriteDao,
    private val mealDetailDao: MealDetailDao
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

    override suspend fun insertMealDetail(mealDetail: MealDetailEntity) {
        mealDetailDao.insertMealDetail(mealDetail)
    }

    override fun getMealDetailById(id: String): Flow<MealDetailEntity?> {
        return mealDetailDao.getMealDetailById(id)
    }

    override suspend fun deleteMealDetailById(id: String) {
        mealDetailDao.deleteMealDetailById(id)
    }

    override suspend fun deleteAllMealDetails() {
        mealDetailDao.deleteAllMealDetails()
    }

}
