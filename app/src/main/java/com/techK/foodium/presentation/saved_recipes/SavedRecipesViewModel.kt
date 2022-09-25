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
    private val deleteRecipe: DeleteRecipeUseCase,
    private val deleteAll: DeleteAllRecipesUseCase,
    private val getRecipesByOrder: GetRecipesByOrderUseCase,
    private val getSortOrder: GetSortOrderUserCase,
    private val setRefreshQuery: SetRefreshQueryUseCase,
    private val setSortOrder: SetSortOrderUserCase,
) : ViewModel() {

    private val _sortOrder = MutableStateFlow(SortOrder.NONE)
    val sortOrder get() = _sortOrder.asStateFlow()

    private val _savedRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val savedRecipes get() = _savedRecipes.asStateFlow()

    init {
        getSortOrder()
    }

    private fun getSortOrder() = viewModelScope.launch {
        getSortOrder.invoke().collect {
            _sortOrder.emit(it)
        }
    }

    fun getSavedListByOrder(sortOrder: SortOrder) = viewModelScope.launch {
        getRecipesByOrder.invoke(sortOrder).collect {
            _savedRecipes.emit(it)
        }
    }

    fun deleteRecipe(recipe: Recipe) = viewModelScope.launch {
        deleteRecipe.invoke(recipe)
    }

    fun setRefreshQuery(refreshList: Boolean) = viewModelScope.launch {
        setRefreshQuery.invoke(refreshList)
    }

    fun deleteAllRecipe() = viewModelScope.launch {
        deleteAll.invoke()
    }

    fun setSortOrder(sortOrder: SortOrder) = viewModelScope.launch {
        setSortOrder.invoke(sortOrder)
    }

}