package com.example.dodo.data.remote.dto


import com.google.gson.annotations.SerializedName

data class CategoriesList(
    @SerializedName("meals")
    val categories: List<CategoryDto>
)