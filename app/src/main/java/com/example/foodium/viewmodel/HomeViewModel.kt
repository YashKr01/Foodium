package com.example.foodium.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodium.data.database.model.RecipeEntity
import com.example.foodium.repository.AppRepository
import com.example.foodium.utils.Constants
import com.example.foodium.utils.Constants.QUERY_API_KEY
import com.example.foodium.utils.Constants.QUERY_TYPE
import com.example.foodium.utils.Resource
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

    val recipesList get() = repository.recipeList
    val refreshList get() = repository.refreshList

    fun searchRecipes(query: String) = viewModelScope.launch {
        repository.getRecipesList(
            hashMapOf(
                Pair(QUERY_TYPE, query),
                Pair(QUERY_API_KEY, Constants.API_KEY.random())
            )
        )
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

    fun getSavedList() = repository.getSavedRecipesList()

    fun updateList(newList: List<RecipeEntity>) = viewModelScope.launch {
        recipesList.emit(Resource.Success(newList))
    }

}