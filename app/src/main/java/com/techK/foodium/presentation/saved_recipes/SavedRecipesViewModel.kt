package com.techK.foodium.presentation.saved_recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techK.foodium.domain.entities.Recipe
import com.techK.foodium.domain.enums.SortOrder
import com.techK.foodium.domain.usecases.database.DeleteAllRecipesUseCase
import com.techK.foodium.domain.usecases.database.DeleteRecipeUseCase
import com.techK.foodium.domain.usecases.database.GetRecipesByOrderUseCase
import com.techK.foodium.domain.usecases.datastore.GetSortOrderUserCase
import com.techK.foodium.domain.usecases.datastore.SetRefreshQueryUseCase
import com.techK.foodium.domain.usecases.datastore.SetSortOrderUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
) : ViewModel() {

    private val _sortOrder = MutableStateFlow(SortOrder.NONE)
    val sortOrder get() = _sortOrder.asStateFlow()

    private val _savedRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val savedRecipes get() = _savedRecipes.asStateFlow()

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
    }

    fun setRefreshQuery(refreshList: Boolean) = viewModelScope.launch {
        setRefreshQueryUseCase(refreshList)
    }

    fun deleteAllRecipe() = viewModelScope.launch {
        deleteAllRecipesUseCase()
    }

    fun setSortOrder(sortOrder: SortOrder) = viewModelScope.launch {
        setSortOrderUserCase(sortOrder)
    }

}