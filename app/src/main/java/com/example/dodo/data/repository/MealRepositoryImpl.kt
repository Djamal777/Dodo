package com.example.dodo.data.repository

import com.example.dodo.data.local.MealDao
import com.example.dodo.data.remote.TheMealdbApi
import com.example.dodo.data.remote.dto.CategoriesList
import com.example.dodo.data.remote.dto.CategoryDto
import com.example.dodo.data.remote.dto.MealDto
import com.example.dodo.data.remote.dto.MealsList
import com.example.dodo.domain.model.Category
import com.example.dodo.domain.model.Meal
import com.example.dodo.domain.repository.MealRepository
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val api:TheMealdbApi,
    private val dao:MealDao
):MealRepository {
    override suspend fun getCategories(): CategoriesList {
        return api.getCategories()
    }

    override suspend fun getMealsByCategory(category: String): MealsList {
        return api.getMealsByCategory(category)
    }

    override suspend fun getCategoriesFromDb(): List<Category> {
        return dao.getCategories()
    }

    override suspend fun getMealsFromDb(): List<Meal> {
        return dao.getMeals()
    }

    override suspend fun insertMeals(meals: List<Meal>) {
        dao.insertMeals(meals)
    }

    override suspend fun deleteMeals() {
        dao.deleteMeals()
    }

    override suspend fun insertCategories(categories: List<Category>) {
        dao.insertCategories(categories)
    }

    override suspend fun deleteCategories() {
        dao.deleteCategories()
    }
}