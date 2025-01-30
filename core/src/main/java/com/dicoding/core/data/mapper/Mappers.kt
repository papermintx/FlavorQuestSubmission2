package com.dicoding.core.data.mapper

import com.dicoding.core.data.local.entity.MealEntity
import com.dicoding.core.data.local.entity.MealFavoriteEntity
import com.dicoding.core.data.remote.response.MealsItemDto
import com.dicoding.core.domain.model.Meal
import com.dicoding.core.domain.model.MealDetail


fun MealsItemDto.toDomain(): Meal {
    return Meal(
        id = idMeal ?: "",
        name = strMeal ?: "Unknown",
        category = strCategory ?: "Unknown",
        thumbnail = strMealThumb ?: "",
        area = strArea ?: "Unknown"
    )
}

fun Meal.toEntity(): MealEntity{
    return MealEntity(
        id = id,
        name = name,
        category = category,
        thumbnail = thumbnail,
        area = area
    )
}

fun MealEntity.toDomain(): Meal {
    return Meal(
        id = id,
        name = name,
        category = category,
        thumbnail = thumbnail,
        area = area
    )
}

fun MealFavoriteEntity.toDomain(): Meal {
    return Meal(
        id = id,
        name = name,
        category = category,
        thumbnail = thumbnail,
        area = area
    )
}

fun Meal.toFavoriteEntity(): MealFavoriteEntity {
    return MealFavoriteEntity(
        id = id,
        name = name,
        category = category,
        thumbnail = thumbnail,
        area = area
    )
}

fun MealsItemDto.toMealDetail(): MealDetail {
    val ingredients = listOfNotNull(
        strIngredient1, strIngredient2, strIngredient3, strIngredient4, strIngredient5,
        strIngredient6, strIngredient7, strIngredient8, strIngredient9, strIngredient10,
        strIngredient11, strIngredient12, strIngredient13, strIngredient14, strIngredient15,
        strIngredient16?.toString(), strIngredient17?.toString(), strIngredient18?.toString(), strIngredient19?.toString(), strIngredient20?.toString()
    ).filter { it.isNotBlank() }

    val measures = listOfNotNull(
        strMeasure1, strMeasure2, strMeasure3, strMeasure4, strMeasure5,
        strMeasure6, strMeasure7, strMeasure8, strMeasure9, strMeasure10,
        strMeasure11, strMeasure12, strMeasure13, strMeasure14, strMeasure15,
        strMeasure16?.toString(), strMeasure17?.toString(), strMeasure18?.toString(), strMeasure19?.toString(), strMeasure20?.toString()
    ).filter { it.isNotBlank() }

    val ingredientsWithMeasures = ingredients.zip(measures)

    return MealDetail(
        idMeal = idMeal ?: "",
        strMeal = strMeal ?: "Unknown",
        strCategory = strCategory ?: "Unknown",
        strArea = strArea ?: "Unknown",
        strInstructions = strInstructions ?: "Unknown",
        strMealThumb = strMealThumb,
        strYoutube = strYoutube ?: "",
        ingredientsWithMeasures = ingredientsWithMeasures
    )
}

fun MealDetail.toMeal(): Meal {
    return Meal(
        id = idMeal,
        name = strMeal,
        category = strCategory,
        thumbnail = strMealThumb ?: "",
        area = strArea
    )
}
