package com.techK.foodium.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techK.foodium.data.NetworkObserver
import com.techK.foodium.domain.entities.Recipe
import com.techK.foodium.domain.usecases.DeleteRecipeUseCase
import com.techK.foodium.domain.usecases.GetRecipesUseCase
import com.techK.foodium.domain.usecases.SaveRecipeUseCase
import com.techK.foodium.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRecipesUseCase: GetRecipesUseCase,
    private val saveRecipeUseCase: SaveRecipeUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase,
    private val networkObserver: NetworkObserver,
) : ViewModel() {

    var selectedCategory: Int = 0

    private val _recipes = MutableStateFlow<Resource<List<Recipe>>>(Resource.Loading())
    val recipes get() = _recipes.asStateFlow()

    private val _connection = MutableStateFlow(true)
    val connection get() = _connection.asStateFlow()

    init {
        getRecipes()
        observeConnection()
    }

    private fun getRecipes() = viewModelScope.launch {
        getRecipesUseCase().collect {
            _recipes.emit(it)
        }
    }

    private fun observeConnection() = viewModelScope.launch {
        networkObserver.observeConnection().collect {
            _connection.emit(it)
        }
    }

    fun saveRecipe(recipe: Recipe) = viewModelScope.launch {
        saveRecipeUseCase(recipe)
    }

    fun deleteRecipe(recipe: Recipe) = viewModelScope.launch {
        deleteRecipeUseCase(recipe)
    }

}