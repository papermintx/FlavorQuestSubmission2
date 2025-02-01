package com.dicoding.core.domain.usecase

import com.dicoding.core.common.State
import com.dicoding.core.data.Resource
import com.dicoding.core.domain.contract.repository.RecipeRepository
import com.dicoding.core.domain.model.Meal
import com.dicoding.core.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class UseCaseTest {

    private lateinit var useCase: SearchMealUseCaseImpl
    private var repository: RecipeRepository = mock()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        useCase = SearchMealUseCaseImpl(repository)
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun search_success() = runTest {
        // Arrange
        val query = "Chicken"
        val meals = listOf(Meal(id = "1", name = "Chicken Soup", category = "Soup", thumbnail = "thumb_url", area = "American"))
        Mockito.`when`(repository.searchRecipes(query)).thenReturn(flow {
            emit(Resource.Loading())
            emit(Resource.Success(meals))
        })

        // Act
        val result = useCase(query).toList()

        // Assert
        assertEquals(result.size, 2)
        assertEquals(State.Loading, result[0])
        assertEquals(State.Success(meals), result[1])
    }

    @Test
    fun search_failed() = runTest {
        // Arrange
        val query = "Chicken"
        val errorMessage = "Network error"
        val exception = Exception(errorMessage)
        Mockito.`when`(repository.searchRecipes(query)).thenReturn(flow {
            emit(Resource.Loading())
            emit(Resource.Error(exception))
        })

        // Act
        val result = useCase(query).toList()

        // Assert
        assertEquals(State.Loading, result[0])
        assertEquals(State.Error(errorMessage), result[1])
    }

    @Test
    fun search_empty() = runTest {
        // Arrange
        val query = "Chicken"
        Mockito.`when`(repository.searchRecipes(query)).thenReturn(flow {
            emit(Resource.Loading())
            emit(Resource.Success(emptyList<Meal>()))
        })

        // Act
        val result = useCase(query).toList()

        // Assert
        assertEquals(State.Loading, result[0])
        assertEquals(State.Error("the recipe you are looking for does not exist"), result[1])
    }
}