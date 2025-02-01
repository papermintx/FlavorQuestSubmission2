package com.dicoding.flavorquest.ui.presentation.detail.viewmodel

import com.dicoding.core.common.State
import com.dicoding.core.domain.contract.usecase.GetMealDetailUseCase
import com.dicoding.core.domain.contract.usecase.InsertFavoriteMealUseCase
import com.dicoding.core.domain.model.MealDetail
import com.dicoding.flavorquest.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class DetailMealViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = MainDispatcherRule()

    private var getMealDetailUseCase: GetMealDetailUseCase = mock()
    private var insertFavoriteMealUseCase: InsertFavoriteMealUseCase = mock()
    private lateinit var detailMealViewModel: DetailMealViewModel

    @Before
    fun setUp() {
        detailMealViewModel = DetailMealViewModel(getMealDetailUseCase, insertFavoriteMealUseCase)
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getDetailMeal_success() = runTest {
        val mealDetail = MealDetail(
            "1", "Chicken", "Main Course", "thumb_url", "American", "Description",
            strMealThumb = "thumb_url",
            ingredientsWithMeasures = listOf(Pair("1", "ingredient1"))
        )

        val expectedState = State.Success(mealDetail)
        `when`(getMealDetailUseCase("1")).thenReturn(flowOf(expectedState))
        detailMealViewModel.onEvent(DetailEvent.GetDetail("1"))

        advanceUntilIdle()

        val state = detailMealViewModel.state.value

        assertNotNull(state)
        assertEquals(false, state.isLoading)
        assertEquals(mealDetail, state.data)
        assertEquals(Pair(false, ""), state.error)
    }

    @Test
    fun getDetailMeal_error() = runTest {
        val expectedState = State.Error("Network Error")
        `when`(getMealDetailUseCase("1")).thenReturn(flowOf(expectedState))
        detailMealViewModel.onEvent(DetailEvent.GetDetail("1"))

        advanceUntilIdle()

        val state = detailMealViewModel.state.value
        assertNotNull(state)
        assertEquals(false, state.isLoading)
        assertEquals(true, state.error.first)
        assertEquals("Network Error", state.error.second)
    }

    @Test
    fun resetError_updatesStateCorrectly() = runTest {
        detailMealViewModel.onEvent(DetailEvent.ResetError)
        advanceUntilIdle()

        val state = detailMealViewModel.state.value
        assertNotNull(state)
        assertEquals(Pair(false, ""), state.error)
    }
}