package com.techK.foodium.presentation.recipe_details

import com.techK.foodium.domain.entities.Recipe

sealed class RecipeDetailsEvents {
    class ShareRecipe(val recipe: Recipe) : RecipeDetailsEvents()
    object SaveRecipe : RecipeDetailsEvents()
    object DeleteRecipe : RecipeDetailsEvents()
}
