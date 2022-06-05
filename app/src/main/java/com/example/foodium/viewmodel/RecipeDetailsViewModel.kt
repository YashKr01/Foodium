package com.example.foodium.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodium.data.database.model.RecipeEntity
import com.example.foodium.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(private val repository: AppRepository) : ViewModel() {

    fun insertRecipe(recipe: RecipeEntity) = viewModelScope.launch {
        repository.insertRecipe(recipe)
    }

    fun deleteRecipe(recipe: RecipeEntity) = viewModelScope.launch {
        repository.deleteRecipe(recipe)
    }

    fun setRefreshQuery(refreshList: Boolean) = viewModelScope.launch {
        repository.setRefreshQuery(refreshList)
    }

}