package com.example.dodo.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.dodo.domain.model.Category
import com.example.dodo.domain.model.Meal

@Dao
interface MealDao {

    @Insert
    suspend fun insertMeals(meals:List<Meal>)

    @Query("DELETE FROM meals")
    suspend fun deleteMeals()

    @Query("SELECT * FROM meals")
    suspend fun getMeals(): List<Meal>

    @Insert
    suspend fun insertCategories(categories:List<Category>)

    @Query("DELETE FROM categories")
    suspend fun deleteCategories()

    @Query("SELECT * FROM categories")
    suspend fun getCategories(): List<Category>
}