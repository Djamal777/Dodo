package com.example.dodo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meals")
data class Meal(
    @PrimaryKey
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
    val price: String,
    val category: String
)
