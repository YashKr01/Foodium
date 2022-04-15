package com.example.foodium.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.foodium.data.database.model.RecipeEntity

class RecipeItemComparator:DiffUtil.ItemCallback<RecipeEntity>() {

    override fun areItemsTheSame(oldItem: RecipeEntity, newItem: RecipeEntity): Boolean =
        oldItem.recipeId == newItem.recipeId

    override fun areContentsTheSame(oldItem: RecipeEntity, newItem: RecipeEntity): Boolean =
        oldItem == newItem

}