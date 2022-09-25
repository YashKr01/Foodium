package com.techK.foodium.presentation.adapters.list_adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.techK.foodium.databinding.ItemRecipeBinding
import com.techK.foodium.domain.entities.Recipe

class SavedRecipeAdapter(
    private val deleteRecipe: (Recipe) -> Unit,
    private val onRecipeClick: (Recipe) -> Unit,
) : ListAdapter<Recipe, SavedRecipeAdapter.RecipeViewHolder>(RecipeItemComparator()) {

    class RecipeViewHolder(
        private val binding: ItemRecipeBinding,
        private val onRecipeClick: (Int) -> Unit,
        private val deleteRecipe: (Int) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.imgDelete.visibility = View.VISIBLE
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) onRecipeClick(position)
            }
            binding.imgDelete.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) deleteRecipe(position)
            }
        }

        fun bindRecipe(item: Recipe) {
            binding.apply {
                recipe = item
                executePendingBindings()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(
            binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onRecipeClick = { position ->
                val recipe = getItem(position)
                if (recipe != null) onRecipeClick(recipe)
            },
            deleteRecipe = { position ->
                val recipe = getItem(position)
                if (recipe != null) deleteRecipe(recipe)
            }
        )
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bindRecipe(it) }
    }

}