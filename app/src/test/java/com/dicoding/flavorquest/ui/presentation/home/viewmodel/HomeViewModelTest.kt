package com.dicoding.flavorquest.ui.presentation.home.viewmodel


import com.dicoding.core.common.State
import com.dicoding.core.domain.contract.usecase.GetAllMealInitialUseCase
import com.dicoding.core.domain.contract.usecase.SearchMealUseCase
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
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = MainDispatcherRule()

    private var searchMealUseCase: SearchMealUseCase = mock()
    private var getAllMealInitialUseCase: GetAllMealInitialUseCase = mock()
    private lateinit var homeViewModel: HomeViewModel

    @Test
    fun fetchInitialMeals_success() = runTest {
        homeViewModel = HomeViewModel(searchMealUseCase, getAllMealInitialUseCase)
        val meals = listOf(Meal("1", "Chicken", "Main Course", "thumb_url", "American"))
        val expectedState = State.Success(meals)
        `when`(getAllMealInitialUseCase()).thenReturn(flowOf(expectedState))
        homeViewModel.onEvent(HomeEvent.FetchInitialMeals)

        advanceUntilIdle()

        val state = homeViewModel.searchState.value

        assertNotNull(state)
        assertEquals(false, state.isLoading)
        assertEquals(meals, state.data)
        assertEquals(Pair(false, ""), state.error)
    }

    @Test
    fun searchMeal_success() = runTest {
        homeViewModel = HomeViewModel(searchMealUseCase, getAllMealInitialUseCase)
        val query = "Chicken"
        val meals = listOf(Meal("1", "Chicken", "Main Course", "thumb_url", "American"))
        val expectedState = State.Success(meals)
        `when`(searchMealUseCase(query)).thenReturn(flowOf(expectedState))
        homeViewModel.onEvent(HomeEvent.Search(query))
        advanceUntilIdle()

        val state = homeViewModel.searchState.value
        assertNotNull(state)
        assertEquals(false, state.isLoading)
        assertEquals(meals, state.data)
        assertEquals(Pair(false, ""), state.error)
    }

    @Test
    fun searchMeal_error() = runTest {
        val query = "Chicken"
        val expectedState = State.Error("Network Error")
        `when`(searchMealUseCase(query)).thenReturn(flowOf(expectedState))
        homeViewModel = HomeViewModel(searchMealUseCase, getAllMealInitialUseCase)
        homeViewModel.onEvent(HomeEvent.Search(query))
        advanceUntilIdle()

        val state = homeViewModel.searchState.value
        assertNotNull(state)
        assertEquals(false, state.isLoading)
        assertEquals(true, state.error.first)
        assertEquals("Network Error", state.error.second)
    }

    @Test
    fun resetSearchError_updatesStateCorrectly() = runTest {
        homeViewModel = HomeViewModel(searchMealUseCase, getAllMealInitialUseCase)
        `when`(getAllMealInitialUseCase()).thenReturn(flowOf(State.Empty))
        `when`(searchMealUseCase(Mockito.anyString())).thenReturn(flowOf(State.Empty))

        homeViewModel.onEvent(HomeEvent.ResetError)
        advanceUntilIdle()

        val state = homeViewModel.searchState.value
        assertNotNull(state)
        assertEquals(Pair(false, ""), state.error)
    }
}