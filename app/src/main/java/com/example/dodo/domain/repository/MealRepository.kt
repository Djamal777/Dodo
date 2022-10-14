package com.example.dodo.domain.repository

import com.example.dodo.data.remote.dto.CategoriesList
import com.example.dodo.data.remote.dto.CategoryDto
import com.example.dodo.data.remote.dto.MealDto
import com.example.dodo.data.remote.dto.MealsList
import com.example.dodo.domain.model.Category
import com.example.dodo.domain.model.Meal

interface MealRepository {

    suspend fun getCategories():CategoriesList

    suspend fun getMealsByCategory(category:String):MealsList

    suspend fun getCategoriesFromDb():List<Category>

    suspend fun getMealsFromDb():List<Meal>

    suspend fun insertMeals(meals:List<Meal>)

    suspend fun deleteMeals()

    suspend fun insertCategories(categories:List<Category>)

    suspend fun deleteCategories()
}