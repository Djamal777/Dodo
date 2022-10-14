package com.example.dodo.data.remote.dto


import com.example.dodo.domain.model.Meal
import com.google.gson.annotations.SerializedName

data class MealDto(
    @SerializedName("idMeal")
    val idMeal: String,
    @SerializedName("strMeal")
    val strMeal: String,
    @SerializedName("strMealThumb")
    val strMealThumb: String
)
fun MealDto.toMeal(category:String): Meal {
    return Meal(
        idMeal = idMeal,
        strMeal = strMeal,
        strMealThumb = strMealThumb,
        price="${(250..1000).random()} руб",
        category = category
    )
}