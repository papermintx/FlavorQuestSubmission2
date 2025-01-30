package com.dicoding.core.domain.model

data class MealDetail(
    val idMeal: String,
    val strMeal: String,
    val strCategory: String,
    val strArea: String,
    val strInstructions: String,
    val strYoutube: String,
    val strMealThumb: String?,
    val ingredientsWithMeasures: List<Pair<String, String>> = emptyList()
)
