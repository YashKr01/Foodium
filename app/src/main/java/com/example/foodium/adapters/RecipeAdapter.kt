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

class RecipeAdapter(
    private val saveRecipe: (RecipeEntity) -> Unit,
    private val deleteRecipe: (RecipeEntity) -> Unit,
    private val onRecipeClick: (RecipeEntity) -> Unit
) : ListAdapter<RecipeEntity, RecipeAdapter.RecipeViewHolder>(RecipeItemComparator()) {

    class RecipeViewHolder(
        private val binding: ItemRecipeBinding,
        private val saveRecipe: (Int) -> Unit,
        private val deleteRecipe: (Int) -> Unit,
        private val onRecipeClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION)
                    onRecipeClick(position)
            }
        }

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

                // on save click listener
                imgFavorite.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        if (item.saved) {
                            deleteRecipe(position)
                            imgFavorite.setImageResource(R.drawable.ic_favorite_hollow)
                        } else {
                            saveRecipe(position)
                            imgFavorite.setImageResource(R.drawable.ic_favorite_solid)
                        }
                        item.saved = !item.saved
                    }
                }

            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder =
        RecipeViewHolder(
            ItemRecipeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            saveRecipe = { position ->
                val article = getItem(position)
                if (article != null) saveRecipe(article)
            },
            deleteRecipe = { position ->
                val article = getItem(position)
                if (article != null) deleteRecipe(article)
            },
            onRecipeClick = { position ->
                val article = getItem(position)
                if (article != null) onRecipeClick(article)
            }
        )

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}