package com.example.dodo.data.remote.dto


import com.google.gson.annotations.SerializedName

data class MealsList(
    @SerializedName("meals")
    val meals: List<MealDto>
)