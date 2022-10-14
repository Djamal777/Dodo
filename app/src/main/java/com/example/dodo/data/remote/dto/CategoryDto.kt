package com.example.dodo.data.remote.dto


import com.example.dodo.domain.model.Category
import com.google.gson.annotations.SerializedName

data class CategoryDto(
    @SerializedName("strCategory")
    val strCategory: String
)
fun CategoryDto.toCategory():Category{
    return Category(
        strCategory = strCategory
    )
}