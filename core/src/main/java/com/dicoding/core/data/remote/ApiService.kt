package com.dicoding.core.data.remote

import com.dicoding.core.data.remote.response.MealsResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api/json/v1/1/search.php")
    suspend fun searchRecipes(
        @Query("s") s: String
    ): Response<MealsResponseDto>

    @GET("api/json/v1/1/lookup.php")
    suspend fun getRecipeDetail(
        @Query("i") i: String
    ): Response<MealsResponseDto>

}