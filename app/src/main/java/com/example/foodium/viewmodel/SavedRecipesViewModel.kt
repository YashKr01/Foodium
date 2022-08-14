package com.example.foodium.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodium.data.database.model.RecipeEntity
import com.example.foodium.repository.AppRepository
import com.example.foodium.utils.SortOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedRecipesViewModel @Inject constructor(private val repository: AppRepository) :
    ViewModel() {

    private val _savedRecipes = MutableStateFlow<List<RecipeEntity>>(emptyList())
    val savedRecipes get() = _savedRecipes.asStateFlow()

    val sortOrder get() = repository.sortOrder

    fun getSavedListByOrder(sortOrder: SortOrder) = viewModelScope.launch {
        repository.getSavedRecipesListByOrder(sortOrder).collect {
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

    fun setSortOrder(sortOrder: SortOrder) = viewModelScope.launch {
        repository.setSortOrder(sortOrder)
    }

}