package com.dicoding.flavorquest.ui.presentation.favorite.viewmodel

import com.dicoding.core.common.State
import com.dicoding.core.domain.contract.usecase.DeleteMealFavoriteUseCase
import com.dicoding.core.domain.contract.usecase.GetAllFavoriteUseCase
import com.dicoding.core.domain.model.Meal
import com.dicoding.flavorquest.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@OptIn(ExperimentalCoroutinesApi::class)
class FavoriteViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = MainDispatcherRule()

    private var getAllFavoriteUseCase: GetAllFavoriteUseCase = mock()
    private var deleteMealFavoriteUseCase: DeleteMealFavoriteUseCase = mock()
    private lateinit var favoriteViewModel: FavoriteViewModel


    @Test
    fun init_success() = runTest {
        val meals = listOf(Meal("1", "Chicken", "Main Course", "thumb_url", "American"))
        val expectedState = State.Success(meals)
        `when`(getAllFavoriteUseCase()).thenReturn(flowOf(expectedState))

        favoriteViewModel = FavoriteViewModel(getAllFavoriteUseCase, deleteMealFavoriteUseCase)
        advanceUntilIdle()

        val state = favoriteViewModel.state.value

        assertNotNull(state)
        assertEquals(false, state.isLoading)
        assertEquals(meals, state.data)
        assertEquals(Pair(false, ""), state.error)
    }

    @Test
    fun init_error() = runTest {
        val expectedState = State.Error("Network Error")
        `when`(getAllFavoriteUseCase()).thenReturn(flowOf(expectedState))

        favoriteViewModel = FavoriteViewModel(getAllFavoriteUseCase, deleteMealFavoriteUseCase)
        advanceUntilIdle()

        val state = favoriteViewModel.state.value

        assertNotNull(state)
        assertEquals(false, state.isLoading)
        assertEquals(true, state.error.first)
        assertEquals("Network Error", state.error.second)
    }

    @Test
    fun deleteFavorite_callsUseCase() = runTest {
        val meal = Meal("1", "Chicken", "Main Course", "thumb_url", "American")
        val expectedState = State.Success(listOf(meal))
        `when`(getAllFavoriteUseCase()).thenReturn(flowOf(expectedState))

        favoriteViewModel = FavoriteViewModel(getAllFavoriteUseCase, deleteMealFavoriteUseCase)
        advanceUntilIdle()

        favoriteViewModel.deleteFavorite(meal)
        advanceUntilIdle()

        verify(deleteMealFavoriteUseCase).invoke(meal)
        assertEquals(false, favoriteViewModel.state.value.isLoading)
    }
}