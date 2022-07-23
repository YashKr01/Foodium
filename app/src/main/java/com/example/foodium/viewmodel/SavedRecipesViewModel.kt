package com.example.foodium.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodium.data.database.model.RecipeEntity
import com.example.foodium.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedRecipesViewModel @Inject constructor(private val repository: AppRepository) :
    ViewModel() {

    init {
        getSavedRecipes()
    }

    private val _savedRecipes = MutableStateFlow<List<RecipeEntity>>(emptyList())
    val savedRecipes get() = _savedRecipes.asStateFlow()

    private fun getSavedRecipes() = viewModelScope.launch {
        repository.getSavedRecipesList().collect {
            _savedRecipes.emit(it)
        }
    }

    fun deleteRecipe(recipeEntity: RecipeEntity) = viewModelScope.launch {
        repository.deleteRecipe(recipeEntity)
    }

    fun setRefreshQuery(refreshList: Boolean) = viewModelScope.launch {
        repository.setRefreshQuery(refreshList)
    }

    fun deleteAllRecipe() = viewModelScope.launch {
        repository.deleteAllRecipes()
    }

}