package com.techK.foodium.presentation.recipe_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techK.foodium.domain.entities.Recipe
import com.techK.foodium.domain.usecases.database.DeleteRecipeUseCase
import com.techK.foodium.domain.usecases.database.SaveRecipeUseCase
import com.techK.foodium.domain.usecases.datastore.SetRefreshQueryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val setRefreshQueryUseCase: SetRefreshQueryUseCase,
    private val saveRecipeUseCase: SaveRecipeUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase,
) : ViewModel() {

    private val _events = Channel<RecipeDetailsEvents>()
    val events = _events.receiveAsFlow()

    fun deleteRecipe(recipe: Recipe) = viewModelScope.launch {
        deleteRecipeUseCase(recipe)
        setRefreshQueryUseCase(true)
        _events.send(RecipeDetailsEvents.DeleteRecipe)
    }

    fun saveRecipe(recipe: Recipe) = viewModelScope.launch {
        saveRecipeUseCase(recipe)
        setRefreshQueryUseCase(true)
        _events.send(RecipeDetailsEvents.SaveRecipe)
    }

    fun shareRecipe(recipe: Recipe) = viewModelScope.launch {
        _events.send(RecipeDetailsEvents.ShareRecipe(recipe))
    }

}