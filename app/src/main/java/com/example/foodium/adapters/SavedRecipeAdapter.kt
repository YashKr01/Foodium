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

class SavedRecipeAdapter(private val deleteRecipe: (RecipeEntity) -> Unit) :
    ListAdapter<RecipeEntity, SavedRecipeAdapter.SavedRecipeViewHolder>(RecipeItemComparator()) {

    class SavedRecipeViewHolder(
        private val binding: ItemSavedRecipeBinding,
        private val deleteRecipe: (Int) -> Unit
    ) :
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

            // Delete Image
            imgDelete.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION)
                    deleteRecipe(position)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedRecipeViewHolder =
        SavedRecipeViewHolder(
            ItemSavedRecipeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            deleteRecipe = { position ->
                val article = getItem(position)
                if (article != null)
                    deleteRecipe(article)
            }
        )

    override fun onBindViewHolder(holder: SavedRecipeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}