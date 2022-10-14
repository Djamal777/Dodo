package com.example.dodo.domain.use_case

import com.example.dodo.common.Resource
import com.example.dodo.data.remote.dto.toMeal
import com.example.dodo.domain.model.Category
import com.example.dodo.domain.model.Meal
import com.example.dodo.domain.repository.MealRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMealsByCategoryUseCase @Inject constructor(
    private val repository: MealRepository
) {
    operator fun invoke(categories:List<Category>): Flow<Resource<List<Meal>>> = flow{
        var meals=mutableListOf<Meal>()
        try {
            emit(Resource.Loading<List<Meal>>())
            categories.forEach{ category->
                meals+=repository.getMealsByCategory(category.strCategory).meals.map{it.toMeal(category.strCategory)}
            }
            emit(Resource.Success<List<Meal>>(meals))
            repository.deleteMeals()
            repository.insertMeals(meals)
        }catch (e: HttpException){
            emit(Resource.Error<List<Meal>>("Неизвестная ошибка"))
            meals=repository.getMealsFromDb().toMutableList()
            emit(Resource.Success<List<Meal>>(meals))
        }catch (e: IOException){
            emit(Resource.Error<List<Meal>>("Нет интернет-соединения"))
            meals=repository.getMealsFromDb().toMutableList()
            emit(Resource.Success<List<Meal>>(meals))
        }
    }
}