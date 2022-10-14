package com.example.dodo.domain.use_case

import com.example.dodo.common.Resource
import com.example.dodo.data.remote.dto.toCategory
import com.example.dodo.domain.model.Category
import com.example.dodo.domain.repository.MealRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: MealRepository
) {
    operator fun invoke():Flow<Resource<List<Category>>> = flow{
        var categories:List<Category>
        try {
            emit(Resource.Loading<List<Category>>())
            categories=repository.getCategories().categories.map{it.toCategory()}
            emit(Resource.Success<List<Category>>(categories))
            repository.deleteCategories()
            repository.insertCategories(categories)
        }catch (e: HttpException){
            emit(Resource.Error<List<Category>>("Неизвестная ошибка"))
            categories=repository.getCategoriesFromDb()
            emit(Resource.Success<List<Category>>(categories))
        }catch (e: IOException){
            emit(Resource.Error<List<Category>>("Нет интернет-соединения"))
            categories=repository.getCategoriesFromDb()
            emit(Resource.Success<List<Category>>(categories))
        }
    }
}