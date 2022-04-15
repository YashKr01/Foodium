package com.example.foodium.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.foodium.R
import com.example.foodium.data.database.model.RecipeEntity
import com.example.foodium.databinding.ItemRecipeBinding
import com.example.foodium.utils.ExtensionFunctions.hide
import com.example.foodium.utils.ExtensionFunctions.show

class RecipeAdapter :
    ListAdapter<RecipeEntity, RecipeAdapter.RecipeViewHolder>(RecipeItemComparator()) {

    inner class RecipeViewHolder(private val binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RecipeEntity) =
            binding.apply {
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

                // saved
                imgFavorite.setImageResource(
                    when {
                        item.saved -> R.drawable.ic_favorite_solid
                        else -> R.drawable.ic_favorite_hollow
                    }
                )

            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder =
        RecipeViewHolder(
            ItemRecipeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}