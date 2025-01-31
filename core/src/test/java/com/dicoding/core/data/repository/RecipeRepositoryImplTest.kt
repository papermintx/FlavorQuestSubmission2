package com.dicoding.core.data.repository

import com.dicoding.core.data.remote.mapper.toDomain
import com.dicoding.core.domain.contract.source.LocalDataSource
import com.dicoding.core.domain.contract.source.RemoteDataSource
import com.dicoding.core.utils.MealDataGenerator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class RecipeRepositoryImplTest {

    private lateinit var remoteDataSource: RemoteDataSource
    private lateinit var localDataSource: LocalDataSource
    private lateinit var recipeRepositoryImpl: RecipeRepositoryImpl

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        remoteDataSource = mock(RemoteDataSource::class.java)
        localDataSource = mock(LocalDataSource::class.java)
        recipeRepositoryImpl = RecipeRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Test
    fun searchRecipes() = runTest(testDispatcher) {
        val query = "chicken"
        val meals = MealDataGenerator.generateDummyMealDto()
        val flow = flowOf(Result.success(meals))

        `when`(remoteDataSource.searchRecipes(query)).thenReturn(flow)

        val result = recipeRepositoryImpl.searchRecipes(query).toList()

        assertEquals(meals.meals!!.size, result[0].size)
        val dataDomain = meals.meals!!.map { it?.toDomain() }
        assertEquals(dataDomain, result[0])
    }
}