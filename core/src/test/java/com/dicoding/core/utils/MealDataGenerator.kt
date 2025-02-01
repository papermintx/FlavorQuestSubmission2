package com.dicoding.core.utils

import com.dicoding.core.data.remote.response.MealsItemDto
import com.dicoding.core.data.remote.response.MealsResponseDto

object MealDataGenerator {

    fun generateDummyMealDto(): MealsResponseDto {
        val meals = mutableListOf<MealsItemDto?>()
        val mealNames = listOf("Chicken Curry", "Beef Stew", "Vegetable Stir Fry", "Spaghetti Bologna", "Fish Tacos")

        mealNames.forEachIndexed { index, name ->
            meals.add(
                MealsItemDto(
                    strImageSource = "https://example.com/image${index + 1}.jpg",
                    strIngredient10 = "Ingredient10",
                    strIngredient12 = "Ingredient12",
                    strIngredient11 = "Ingredient11",
                    strIngredient14 = "Ingredient14",
                    strCategory = "Category${index + 1}",
                    strIngredient13 = "Ingredient13",
                    strIngredient16 = "Ingredient16",
                    strIngredient15 = "Ingredient15",
                    strIngredient18 = "Ingredient18",
                    strIngredient17 = "Ingredient17",
                    strArea = "Area${index + 1}",
                    strCreativeCommonsConfirmed = "Yes",
                    strIngredient19 = "Ingredient19",
                    strTags = "Tag1,Tag2",
                    idMeal = "${index + 1}",
                    strInstructions = "Instructions${index + 1}",
                    strIngredient1 = "Ingredient1",
                    strIngredient3 = "Ingredient3",
                    strIngredient2 = "Ingredient2",
                    strIngredient20 = "Ingredient20",
                    strIngredient5 = "Ingredient5",
                    strIngredient4 = "Ingredient4",
                    strIngredient7 = "Ingredient7",
                    strIngredient6 = "Ingredient6",
                    strIngredient9 = "Ingredient9",
                    strIngredient8 = "Ingredient8",
                    strMealThumb = "https://example.com/thumb${index + 1}.jpg",
                    strMeasure20 = "Measure20",
                    strYoutube = "https://youtube.com/video${index + 1}",
                    strMeal = name,
                    strMeasure12 = "Measure12",
                    strMeasure13 = "Measure13",
                    strMeasure10 = "Measure10",
                    strMeasure11 = "Measure11",
                    dateModified = "2023-01-01",
                    strDrinkAlternate = "DrinkAlternate${index + 1}",
                    strSource = "Source${index + 1}",
                    strMeasure9 = "Measure9",
                    strMeasure7 = "Measure7",
                    strMeasure8 = "Measure8",
                    strMeasure5 = "Measure5",
                    strMeasure6 = "Measure6",
                    strMeasure3 = "Measure3",
                    strMeasure4 = "Measure4",
                    strMeasure1 = "Measure1",
                    strMeasure18 = "Measure18",
                    strMeasure2 = "Measure2",
                    strMeasure19 = "Measure19",
                    strMeasure16 = "Measure16",
                    strMeasure17 = "Measure17",
                    strMeasure14 = "Measure14",
                    strMeasure15 = "Measure15"
                )
            )
        }

        return MealsResponseDto(meals = meals)
    }
}