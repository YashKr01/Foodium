package com.techK.foodium.presentation.adapters.list_adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.techK.foodium.R
import com.techK.foodium.databinding.ItemRecipeBinding
import com.techK.foodium.domain.entities.Recipe

class RecipeAdapter(
    private val saveRecipe: (Recipe) -> Unit,
    private val deleteRecipe: (Recipe) -> Unit,
    private val onRecipeClick: (Recipe) -> Unit,
) : ListAdapter<Recipe, RecipeAdapter.RecipeViewHolder>(RECIPE_COMPARATOR) {

    inner class RecipeViewHolder(
        private val binding: ItemRecipeBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.imgSave.visibility = View.VISIBLE
        }

        fun bind(item: Recipe) {

            binding.apply {
                recipe = item
                recipeAdapter = this@RecipeAdapter
                imgSave.setImageResource(
                    if (item.saved) R.drawable.ic_favorite_solid
                    else R.drawable.ic_favorite_hollow
                )
                executePendingBindings()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder =
        RecipeViewHolder(
            ItemRecipeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ))

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val RECIPE_COMPARATOR = object : DiffUtil.ItemCallback<Recipe>() {
            override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean =
                oldItem == newItem
        }
    }

    fun onSaveClick(recipe: Recipe, view: ImageView) {
        when (recipe.saved) {
            true -> {
                deleteRecipe(recipe)
                view.setImageLevel(R.drawable.ic_favorite_hollow)
            }
            false -> {
                saveRecipe(recipe)
                view.setImageResource(R.drawable.ic_favorite_solid)
            }
        }
        recipe.saved = !recipe.saved
    }

    fun onItemClick(recipe: Recipe) {
        this.onRecipeClick(recipe)
    }

}