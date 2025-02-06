package com.dicoding.core.data.repository

import android.util.Log
import com.dicoding.core.data.Resource
import com.dicoding.core.data.mapper.toDetailEntity
import com.dicoding.core.data.mapper.toDomain
import com.dicoding.core.data.mapper.toEntity
import com.dicoding.core.data.mapper.toFavoriteEntity
import com.dicoding.core.data.mapper.toMealDetail
import com.dicoding.core.data.remote.response.MealsItemDto
import com.dicoding.core.data.remote.response.MealsResponseDto
import com.dicoding.core.domain.contract.source.LocalDataSource
import com.dicoding.core.domain.contract.source.RemoteDataSource
import com.dicoding.core.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class RecipeRepositoryImplTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: RecipeRepositoryImpl
    private var remoteDataSource: RemoteDataSource = mock()
    private var localDataSource: LocalDataSource = mock()

    @Before
    fun setUp() {
        repository = RecipeRepositoryImpl(remoteDataSource, localDataSource)
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun searchRecipes_success() = runTest {
        // Arrange
        val query = "Chicken"
        val mealsDto = listOf(
            MealsItemDto(idMeal = "1", strMeal = "Chicken Soup", strCategory = "Soup", strMealThumb = "thumb_url", strArea = "American")
        )
        val mealsResponseDto = MealsResponseDto(meals = mealsDto)
        val meals = mealsDto.map { it.toDomain() }
        val mealsEntity = mealsDto.map { it.toDomain().toEntity() }

        Mockito.`when`(remoteDataSource.searchRecipes(query)).thenReturn(flowOf(Result.success(mealsResponseDto)))
        Mockito.`when`(localDataSource.getAllMeals()).thenReturn(flowOf(mealsEntity))

        // Act
        val result = repository.searchRecipes(query).toList()

        // Assert
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Success)
        assertEquals(meals, (result[1] as Resource.Success).data)
    }

    @Test
    fun searchRecipes_error() = runTest {
        // Arrange
        val query = "Chicken"
        val exception = Exception("Network error")

        Mockito.`when`(remoteDataSource.searchRecipes(query)).thenReturn(flowOf(Result.failure(exception)))
        Mockito.`when`(localDataSource.getAllMeals()).thenReturn(flowOf(emptyList()))

        // Act
        val result = repository.searchRecipes(query).toList()

        // Assert
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Error)
        assertEquals(exception.message, (result[1] as Resource.Error).message)
    }

    @Test
    fun getInitialMeal_success() = runTest {
        // Arrange
        val mealsDto = listOf(
            MealsItemDto(idMeal = "1", strMeal = "Chicken Soup", strCategory = "Soup", strMealThumb = "thumb_url", strArea = "American")
        )
        val mealsEntity = mealsDto.map { it.toDomain().toEntity() }

        Mockito.`when`(remoteDataSource.getInitialMeal()).thenReturn(flowOf(Result.success(mealsDto)))
        Mockito.`when`(localDataSource.getAllMeals()).thenReturn(flowOf(mealsEntity))

        // Act
        val result = repository.getInitialMeal().toList()
        Log.d("result", result.toString())

        // Assert
        assertTrue(result.size == 1)
        assertTrue(result[0] is Resource.Success)
    }

    @Test
    fun getRecipeDetail_success() = runTest {
        // Arrange
        val mealId = "1"
        val mealDto = MealsItemDto(
            idMeal = mealId,
            strMeal = "Chicken Soup",
            strCategory = "Soup",
            strMealThumb = "thumb_url",
            strArea = "American",
            strInstructions = "Cook the chicken soup",
        )
        val mealDetail = mealDto.toMealDetail()

        Mockito.`when`(remoteDataSource.getRecipeDetail(mealId)).thenReturn(flowOf(Result.success(mealDto)))
        Mockito.`when`(localDataSource.getMealDetailById(mealId)).thenReturn(flowOf(mealDto.toDetailEntity()))

        // Act
        val result = repository.getRecipeDetail(mealId).toList()
        println(result)

        // Assert
        assertTrue(result[0] is Resource.Success)
        assertEquals(mealDetail, (result[0] as Resource.Success).data)
    }

    @Test
    fun getRecipeDetail_error() = runTest {
        // Arrange
        val mealId = "1"
        val exception = Exception("Network error")

        Mockito.`when`(remoteDataSource.getRecipeDetail(mealId)).thenReturn(flowOf(Result.failure(exception)))

        // Act
        val result = repository.getRecipeDetail(mealId).toList()

        // Assert
        assertTrue(result[0] is Resource.Loading) // State pertama harus Loading
        assertTrue(result[1] is Resource.Error) // State kedua harus Error
        assertEquals(exception.message, (result[1] as Resource.Error).message) // Pesan error harus sesuai
    }

    @Test
    fun getAllFavoriteMeal_success() = runTest {
        // Arrange
        val mealsDto = listOf(
            MealsItemDto(idMeal = "1", strMeal = "Chicken Soup", strCategory = "Soup", strMealThumb = "thumb_url", strArea = "American")
        )
        val meals = mealsDto.map { it.toDomain() }
        val entity = meals.map { it.toFavoriteEntity() }

        Mockito.`when`(localDataSource.getAllMealsFavorite()).thenReturn(flowOf(entity))

        // Act
        val result = repository.getAllFavoriteMeal().toList()

        // Assert
        assertEquals(meals, result[0])
    }

    @Test
    fun getAllFavoriteMeal_empty() = runTest {
        // Arrange
        Mockito.`when`(localDataSource.getAllMealsFavorite()).thenReturn(flowOf(emptyList()))

        // Act
        val result = repository.getAllFavoriteMeal().toList()

        // Assert
        assertTrue(result[0].isEmpty())
    }

    @Test
    fun insertFavoriteMealToDatabase_success() = runTest {
        // Arrange
        val mealDto = MealsItemDto(idMeal = "1", strMeal = "Chicken Soup", strCategory = "Soup", strMealThumb = "thumb_url", strArea = "American")
        val meal = mealDto.toDomain()

        // Act
        repository.insertFavoriteMealToDatabase(meal)

        // Assert
        Mockito.verify(localDataSource).insertMealsFavorite(meal.toFavoriteEntity())
    }

}