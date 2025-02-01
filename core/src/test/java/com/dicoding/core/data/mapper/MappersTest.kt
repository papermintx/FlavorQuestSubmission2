package com.dicoding.core.data.mapper

import com.dicoding.core.data.local.room.entity.MealEntity
import com.dicoding.core.data.local.room.entity.MealFavoriteEntity
import com.dicoding.core.data.remote.response.MealsItemDto
import com.dicoding.core.domain.model.Meal
import com.dicoding.core.domain.model.MealDetail
import org.junit.Assert.assertEquals
import org.junit.Test


class MappersTest {

    @Test
    fun testMealsItemDtoToDomain() {
        val dto = MealsItemDto(
            idMeal = "1",
            strMeal = "Chicken",
            strCategory = "Main Course",
            strMealThumb = "thumb_url",
            strArea = "American"
        )
        val domain = dto.toDomain()
        assertEquals("1", domain.id)
        assertEquals("Chicken", domain.name)
        assertEquals("Main Course", domain.category)
        assertEquals("thumb_url", domain.thumbnail)
        assertEquals("American", domain.area)
    }

    @Test
    fun testMealToEntity() {
        val meal = Meal(
            id = "1",
            name = "Chicken",
            category = "Main Course",
            thumbnail = "thumb_url",
            area = "American"
        )
        val entity = meal.toEntity()
        assertEquals("1", entity.id)
        assertEquals("Chicken", entity.name)
        assertEquals("Main Course", entity.category)
        assertEquals("thumb_url", entity.thumbnail)
        assertEquals("American", entity.area)
    }

    @Test
    fun testMealEntityToDomain() {
        val entity = MealEntity(
            id = "1",
            name = "Chicken",
            category = "Main Course",
            thumbnail = "thumb_url",
            area = "American"
        )
        val domain = entity.toDomain()
        assertEquals("1", domain.id)
        assertEquals("Chicken", domain.name)
        assertEquals("Main Course", domain.category)
        assertEquals("thumb_url", domain.thumbnail)
        assertEquals("American", domain.area)
    }

    @Test
    fun testMealFavoriteEntityToDomain() {
        val favoriteEntity = MealFavoriteEntity(
            id = "1",
            name = "Chicken",
            category = "Main Course",
            thumbnail = "thumb_url",
            area = "American"
        )
        val domain = favoriteEntity.toDomain()
        assertEquals("1", domain.id)
        assertEquals("Chicken", domain.name)
        assertEquals("Main Course", domain.category)
        assertEquals("thumb_url", domain.thumbnail)
        assertEquals("American", domain.area)
    }

    @Test
    fun testMealToFavoriteEntity() {
        val meal = Meal(
            id = "1",
            name = "Chicken",
            category = "Main Course",
            thumbnail = "thumb_url",
            area = "American"
        )
        val favoriteEntity = meal.toFavoriteEntity()
        assertEquals("1", favoriteEntity.id)
        assertEquals("Chicken", favoriteEntity.name)
        assertEquals("Main Course", favoriteEntity.category)
        assertEquals("thumb_url", favoriteEntity.thumbnail)
        assertEquals("American", favoriteEntity.area)
    }

    @Test
    fun testMealsItemDtoToMealDetail() {
        val dto = MealsItemDto(
            idMeal = "1",
            strMeal = "Chicken",
            strCategory = "Main Course",
            strMealThumb = "thumb_url",
            strArea = "American",
            strInstructions = "Cook it well",
            strYoutube = "youtube_url",
            strIngredient1 = "Chicken",
            strMeasure1 = "1 kg"
        )
        val detail = dto.toMealDetail()
        assertEquals("1", detail.idMeal)
        assertEquals("Chicken", detail.strMeal)
        assertEquals("Main Course", detail.strCategory)
        assertEquals("thumb_url", detail.strMealThumb)
        assertEquals("American", detail.strArea)
        assertEquals("Cook it well", detail.strInstructions)
        assertEquals("youtube_url", detail.strYoutube)
        assertEquals(listOf("Chicken" to "1 kg"), detail.ingredientsWithMeasures)
    }

    @Test
    fun testMealDetailToMeal() {
        val detail = MealDetail(
            idMeal = "1",
            strMeal = "Chicken",
            strCategory = "Main Course",
            strMealThumb = "thumb_url",
            strArea = "American",
            strInstructions = "Cook it well",
            strYoutube = "youtube_url",
            ingredientsWithMeasures = listOf("Chicken" to "1 kg")
        )
        val meal = detail.toMeal()
        assertEquals("1", meal.id)
        assertEquals("Chicken", meal.name)
        assertEquals("Main Course", meal.category)
        assertEquals("thumb_url", meal.thumbnail)
        assertEquals("American", meal.area)
    }
}