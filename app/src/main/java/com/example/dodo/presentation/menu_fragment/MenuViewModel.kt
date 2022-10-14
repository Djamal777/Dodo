package com.example.dodo.presentation.menu_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.example.dodo.common.Resource
import com.example.dodo.domain.model.Category
import com.example.dodo.domain.model.Meal
import com.example.dodo.domain.use_case.GetCategoriesUseCase
import com.example.dodo.domain.use_case.GetMealsByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getMealsByCategoryUseCase: GetMealsByCategoryUseCase
) : ViewModel() {

    private val _categories: MutableLiveData<Resource<List<Category>>> = MutableLiveData()
    val categories: LiveData<Resource<List<Category>>> = _categories

    private val _meals: MutableLiveData<Resource<List<Meal>>> = MutableLiveData()
    val meals: LiveData<Resource<List<Meal>>> = _meals

    lateinit var currentCategory:Category

    init {
        viewModelScope.launch(Dispatchers.IO){
            getCategories()
            delay(100)
            getMeals()
        }
    }

    private suspend fun getMeals() {
        _categories.value?.data?.let {
            getMealsByCategoryUseCase(it).collect { result ->
                _meals.postValue(result)
            }
        }
    }

    private suspend fun getCategories() {
        getCategoriesUseCase().collect { result ->
            _categories.postValue(result)
        }
    }
}