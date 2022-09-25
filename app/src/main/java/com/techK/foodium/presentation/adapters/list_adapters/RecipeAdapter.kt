package com.techK.foodium.presentation.adapters.list_adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.techK.foodium.R
import com.techK.foodium.databinding.ItemRecipeBinding
import com.techK.foodium.domain.entities.Recipe

class RecipeAdapter(
    private val saveRecipe: (Recipe) -> Unit,
    private val deleteRecipe: (Recipe) -> Unit,
    private val onRecipeClick: (Recipe) -> Unit,
) : ListAdapter<Recipe, RecipeAdapter.RecipeViewHolder>(RecipeItemComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(
            binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onRecipeClick = { position ->
                val recipe = getItem(position)
                if (recipe != null) onRecipeClick(recipe)
            },
            saveRecipe = {position ->
                val recipe = getItem(position)
                if (recipe != null) saveRecipe(recipe)
            },
            deleteRecipe = {position ->
                val recipe = getItem(position)
                if (recipe != null) deleteRecipe(recipe)
            }
        )
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bindRecipe(it) }
    }

    class RecipeViewHolder(
        private val binding: ItemRecipeBinding,
        private val onRecipeClick: (Int) -> Unit,
        private val saveRecipe: (Int) -> Unit,
        private val deleteRecipe: (Int) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.imgSave.visibility = View.VISIBLE
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) onRecipeClick(position)
            }
        }

        fun bindRecipe(item: Recipe) {
            binding.apply {
                recipe = item
                imgSave.setImageResource(
                    if (item.saved) R.drawable.ic_favorite_solid
                    else R.drawable.ic_favorite_hollow
                )
                imgSave.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        if (item.saved) {
                            deleteRecipe(position)
                            imgSave.setImageResource(R.drawable.ic_favorite_hollow)
                        } else {
                            saveRecipe(position)
                            imgSave.setImageResource(R.drawable.ic_favorite_solid)
                        }
                        item.saved = !item.saved
                    }
                }
                executePendingBindings()
            }
        }
    }

}