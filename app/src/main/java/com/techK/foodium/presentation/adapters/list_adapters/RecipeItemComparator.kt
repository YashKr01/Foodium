package com.techK.foodium.presentation.adapters.list_adapters

import androidx.recyclerview.widget.DiffUtil
import com.techK.foodium.domain.entities.Recipe

class RecipeItemComparator : DiffUtil.ItemCallback<Recipe>() {

    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem == newItem
    }
}