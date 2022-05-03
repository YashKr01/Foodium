package com.example.foodium.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.foodium.R
import com.example.foodium.data.database.model.RecipeEntity
import com.example.foodium.databinding.ActivityRecipeDetailsBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable

class RecipeDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeDetailsBinding

    private val recipeEntity by navArgs<RecipeDetailsActivityArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        updateUI(recipeEntity.recipe)

    }

    private fun updateUI(recipeEntity: RecipeEntity) {

        binding.apply {

            // title
            textRecipeTitle.text = recipeEntity.title

            // description
            textDescription.text = recipeEntity.summary

            // image
            Glide.with(this@RecipeDetailsActivity)
                .load(recipeEntity.image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageRecipe)

            // chips
            chipVegan.isVisible = recipeEntity.vegan
            chipDairyFree.isVisible=recipeEntity.dairyFree
            chipGlutenFree.isVisible = recipeEntity.glutenFree
            chipPopular.isVisible = recipeEntity.popular
            chipHealthy.isVisible = recipeEntity.veryHealthy
            chipVegetarian.isVisible = recipeEntity.vegetarian

            ratingBar.rating = (recipeEntity.healthScore / 10).toFloat()

        }

    }

}