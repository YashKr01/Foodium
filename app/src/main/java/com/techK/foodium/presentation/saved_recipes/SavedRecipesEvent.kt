package com.techK.foodium.presentation.saved_recipes

import com.techK.foodium.domain.entities.Recipe

sealed class SavedRecipesEvent {
    class ShowRecipeDeletedMessage(val recipe: Recipe) : SavedRecipesEvent()
    class NavigateToDetailsScreen(val recipe: Recipe) : SavedRecipesEvent()
}