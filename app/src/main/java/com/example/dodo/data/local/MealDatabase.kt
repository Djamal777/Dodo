package com.example.dodo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dodo.domain.model.Category
import com.example.dodo.domain.model.Meal

@Database(
    entities =[
        Category::class,
        Meal::class
    ], version = 1
)
abstract class MealDatabase:RoomDatabase() {

    abstract fun mealDao():MealDao
}