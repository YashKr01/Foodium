package com.techK.foodium.presentation.recipe_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techK.foodium.domain.entities.Recipe
import com.techK.foodium.domain.usecases.database.DeleteRecipeUseCase
import com.techK.foodium.domain.usecases.database.SaveRecipeUseCase
import com.techK.foodium.domain.usecases.datastore.SetRefreshQueryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val setRefreshQueryUseCase: SetRefreshQueryUseCase,
    private val saveRecipeUseCase: SaveRecipeUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase,
) : ViewModel() {

    fun setRefreshQuery(refresh: Boolean) = viewModelScope.launch {
        setRefreshQueryUseCase(refresh)
    }

    fun deleteRecipe(recipe: Recipe) = viewModelScope.launch {
        deleteRecipeUseCase(recipe)
    }

    fun insertRecipe(recipe: Recipe) = viewModelScope.launch {
        saveRecipeUseCase(recipe)
    }


}