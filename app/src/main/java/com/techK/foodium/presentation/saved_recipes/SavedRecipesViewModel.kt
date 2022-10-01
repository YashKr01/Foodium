package com.techK.foodium.presentation.saved_recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techK.foodium.domain.entities.Recipe
import com.techK.foodium.domain.enums.SortOrder
import com.techK.foodium.domain.usecases.database.*
import com.techK.foodium.domain.usecases.datastore.GetSortOrderUserCase
import com.techK.foodium.domain.usecases.datastore.SetRefreshQueryUseCase
import com.techK.foodium.domain.usecases.datastore.SetSortOrderUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedRecipesViewModel @Inject constructor(
    private val deleteRecipeUseCase: DeleteRecipeUseCase,
    private val deleteAllRecipesUseCase: DeleteAllRecipesUseCase,
    private val getRecipesByOrderUseCase: GetRecipesByOrderUseCase,
    private val getSortOrderUserCase: GetSortOrderUserCase,
    private val setRefreshQueryUseCase: SetRefreshQueryUseCase,
    private val setSortOrderUserCase: SetSortOrderUserCase,
    private val saveRecipeUseCase: SaveRecipeUseCase,
    private val searchRecipeUseCase: SearchRecipeUseCase,
) : ViewModel() {

    private val _sortOrder = MutableStateFlow(SortOrder.NONE)
    val sortOrder get() = _sortOrder.asStateFlow()

    private val _savedRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val savedRecipes get() = _savedRecipes.asStateFlow()

    private val _event = Channel<SavedRecipesEvent>()
    val event = _event.receiveAsFlow()

    val searchQuery = MutableStateFlow("")
    private var searchJob: Job? = null

    init {
        getSortOrder()
    }

    private fun getSortOrder() = viewModelScope.launch {
        getSortOrderUserCase().collect {
            _sortOrder.emit(it)
        }
    }

    fun getSavedListByOrder(sortOrder: SortOrder) = viewModelScope.launch {
        getRecipesByOrderUseCase(sortOrder).collect {
            _savedRecipes.emit(it)
        }
    }

    fun deleteRecipe(recipe: Recipe) = viewModelScope.launch {
        deleteRecipeUseCase(recipe)
        setRefreshQueryUseCase(true)
        _event.send(SavedRecipesEvent.ShowRecipeDeletedMessage(recipe))
    }

    fun deleteAllRecipe() = viewModelScope.launch {
        deleteAllRecipesUseCase()
        setRefreshQueryUseCase(true)
    }

    fun setSortOrder(sortOrder: SortOrder) = viewModelScope.launch {
        setSortOrderUserCase(sortOrder)
    }

    fun saveRecipe(recipe: Recipe) = viewModelScope.launch {
        saveRecipeUseCase(recipe)
    }

    fun searchRecipe(query: String) = viewModelScope.launch {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            searchRecipeUseCase(query, sortOrder.value).collect {
                _savedRecipes.emit(it)
            }
        }
    }

    fun sortCurrentList(sortOrder: SortOrder) = viewModelScope.launch {
        when (sortOrder) {
            SortOrder.BY_LIKES -> {
                val sortedList = _savedRecipes.value.sortedBy { it.aggregateLikes }
                _savedRecipes.emit(sortedList)
            }
            SortOrder.BY_TIME -> {
                val sortedList = _savedRecipes.value.sortedBy { it.time }
                _savedRecipes.emit(sortedList)
            }
            SortOrder.BY_NAME -> {
                val sortedList = _savedRecipes.value.sortedBy { it.title }
                _savedRecipes.emit(sortedList)
            }
            SortOrder.NONE -> Unit
        }
    }

    fun navigateToDetailsScreen(recipe: Recipe) = viewModelScope.launch {
        _event.send(SavedRecipesEvent.NavigateToDetailsScreen(recipe))
    }

}