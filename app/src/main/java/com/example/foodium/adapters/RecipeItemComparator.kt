package com.example.foodium.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.foodium.data.network.model.Result

class RecipeItemComparator:DiffUtil.ItemCallback<Result>() {

    override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean =
        oldItem.recipeId == newItem.recipeId

    override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean =
        oldItem == newItem

}