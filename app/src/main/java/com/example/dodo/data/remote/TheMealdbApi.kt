package com.example.dodo.data.remote

import com.example.dodo.data.remote.dto.CategoriesList
import com.example.dodo.data.remote.dto.CategoryDto
import com.example.dodo.data.remote.dto.MealDto
import com.example.dodo.data.remote.dto.MealsList
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMealdbApi {

    @GET("api/json/v1/1/list.php?c=list")
    suspend fun getCategories():CategoriesList

    @GET("api/json/v1/1/filter.php")
    suspend fun getMealsByCategory(@Query("c") category:String):MealsList
}