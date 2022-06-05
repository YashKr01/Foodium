package com.example.foodium.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodium.data.database.model.RecipeEntity
import com.example.foodium.repository.AppRepository
import com.example.foodium.utils.Constants.QUERY_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: AppRepository) : ViewModel() {

    init {
        viewModelScope.launch { repository.getRecipesList(null) }
    }

    var selectedCategory = 0

    val recipesList get() = repository.recipeList.asStateFlow()
    val refreshList get() = repository.refreshList

    fun searchRecipes(query: String) = viewModelScope.launch {
        repository.getRecipesList(hashMapOf(Pair(QUERY_TYPE, query)))
    }

    fun saveRecipe(recipeEntity: RecipeEntity) = viewModelScope.launch {
        repository.insertRecipe(recipeEntity)
    }

    fun deleteRecipe(recipeEntity: RecipeEntity) = viewModelScope.launch {
        repository.deleteRecipe(recipeEntity)
    }

    fun setRefreshQuery() = viewModelScope.launch {
        repository.setRefreshQuery(false)
    }

}