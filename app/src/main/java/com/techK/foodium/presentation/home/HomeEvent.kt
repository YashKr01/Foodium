package com.techK.foodium.presentation.home

import com.techK.foodium.domain.entities.Recipe

sealed class HomeEvent {
    object ShowRecipeSavedMessage : HomeEvent()
    object ShowRecipeDeletedMessage : HomeEvent()
    class NavigateToDetailsScreen(val recipe: Recipe) : HomeEvent()
}