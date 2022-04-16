package com.example.foodium.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.foodium.data.database.model.RecipeEntity
import com.example.foodium.databinding.ItemSavedRecipeBinding
import com.example.foodium.utils.ExtensionFunctions.hide
import com.example.foodium.utils.ExtensionFunctions.show

class SavedRecipesAdapter :
    ListAdapter<RecipeEntity, SavedRecipesAdapter.SavedRecipeViewHolder>(RecipeItemComparator()) {

    class SavedRecipeViewHolder(private val binding: ItemSavedRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RecipeEntity) = binding.apply {
            // title
            txtRecipeTitle.text = item.title

            // description
            txtRecipeDescription.text = item.summary

            // likes
            chipLikes.text = item.aggregateLikes.toString()

            // time
            chipTime.text = item.readyInMinutes.toString()

            // vegan
            if (item.vegan) chipVeg.show() else chipVeg.hide()

            // image
            Glide.with(itemView)
                .load(item.image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageRecipe)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedRecipeViewHolder =
        SavedRecipeViewHolder(
            ItemSavedRecipeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: SavedRecipeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}