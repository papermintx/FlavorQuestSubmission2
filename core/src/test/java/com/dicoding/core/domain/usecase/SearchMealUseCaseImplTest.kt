package com.dicoding.core.domain.usecase

import com.dicoding.core.common.State
import com.dicoding.core.data.Resource
import com.dicoding.core.domain.contract.repository.RecipeRepository
import com.dicoding.core.domain.model.Meal
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class SearchMealUseCaseImplTest {

    @Mock
    private lateinit var repository: RecipeRepository

    private lateinit var searchMealUseCase: SearchMealUseCaseImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        searchMealUseCase = SearchMealUseCaseImpl(repository)
    }


    @Test
    fun `invoke should emit success state when repository returns success`() = runTest {
        val query = "test"
        val meals = MealDummyData.getMeals()
        val resource : Resource<List<Meal?>> = Resource.Success(meals)

        `when`(repository.searchRecipes(query)).thenReturn(flow {
            emit(
                resource
            )
        })

        val result = searchMealUseCase(query).toList()

        assertEquals(State.Success(meals), result[0])
    }

    @Test
    fun `invoke should emit error state when repository returns error`() = runTest {
        val query = "test"
        val exception = Exception("An error occurred")
        val errorMessage = "An error occurred"
        val resource : Resource<List<Meal?>> = Resource.Error(exception, null)

        `when`(repository.searchRecipes(query)).thenReturn(flow {
            emit(resource)
        })

        val result = searchMealUseCase(query).toList()

        assertEquals(State.Error(errorMessage), result[0])
    }

    @Test
    fun `invoke should emit error state when repository returns empty success`() = runTest {
        val query = "test"
        val resource : Resource<List<Meal?>> = Resource.Success(emptyList())
        `when`(repository.searchRecipes(query)).thenReturn(flow {
            emit(resource)
        })

        val result = searchMealUseCase(query).toList()

        assertEquals(State.Error("the recipe you are looking for does not exist"), result[0])
    }
}

object MealDummyData {
    fun getMeals(): List<Meal> {
        return listOf(
            Meal(
                id = "1",
                name = "Meal1",
                category = "Category1",
                thumbnail = "Thumbnail1",
                area = "Area1"
            ),
            Meal(
                id = "2",
                name = "Meal2",
                category = "Category2",
                thumbnail = "Thumbnail2",
                area = "Area2"
            ),
            Meal(
                id = "3",
                name = "Meal3",
                category = "Category3",
                thumbnail = "Thumbnail3",
                area = "Area3"
            )
        )
    }
}