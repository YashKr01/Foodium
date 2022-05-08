package com.example.foodium.ui.activities

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.foodium.R
import com.example.foodium.data.database.model.RecipeEntity
import com.example.foodium.databinding.ActivityRecipeDetailsBinding
import com.example.foodium.viewmodel.RecipeDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeDetailsBinding

    private val recipeEntity by navArgs<RecipeDetailsActivityArgs>()

    private val viewModel by viewModels<RecipeDetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        updateUI(recipeEntity.recipe)

        binding.textLink.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(recipeEntity.recipe.sourceUrl)))
        }

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
            chipDairyFree.isVisible = recipeEntity.dairyFree
            chipGlutenFree.isVisible = recipeEntity.glutenFree
            chipPopular.isVisible = recipeEntity.popular
            chipHealthy.isVisible = recipeEntity.veryHealthy
            chipVegetarian.isVisible = recipeEntity.vegetarian

            // rating bar
            ratingBar.rating = (recipeEntity.healthScore / 10).toFloat()

            // link
            textLink.text = recipeEntity.sourceUrl
            textLink.paintFlags = textLink.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        }

    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        try {
            if (recipeEntity.recipe.saved) {
                val icon = menu?.findItem(R.id.menu_save)
                icon?.let { changeMenuIcon(it, R.drawable.ic_favorite_solid) }
            }
        } catch (e: Exception) {

        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.recipe_details_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            // share RecipeURL
            R.id.menu_share -> {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_SUBJECT, "Recipe URL")
                    putExtra(Intent.EXTRA_TEXT, recipeEntity.recipe.sourceUrl)
                    startActivity(this)
                }
                true
            }

            // Insert/Delete Recipe
            else -> {

                try {

                    // If already saved
                    if (recipeEntity.recipe.saved) {
                        viewModel.deleteRecipe(recipeEntity.recipe)
                        recipeEntity.recipe.saved = false
                        changeMenuIcon(item, R.drawable.ic_favorite_hollow)
                    }

                    // if not saved
                    else {
                        viewModel.insertRecipe(recipeEntity.recipe)
                        recipeEntity.recipe.saved = true
                        changeMenuIcon(item, R.drawable.ic_favorite_solid)
                    }
                } catch (e: Exception) {

                }

                true
            }
        }
    }

    private fun changeMenuIcon(item: MenuItem, icon: Int) = item.setIcon(icon)

}