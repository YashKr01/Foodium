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
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.foodium.R
import com.example.foodium.adapters.IngredientAdapter
import com.example.foodium.data.database.model.RecipeEntity
import com.example.foodium.databinding.ActivityRecipeDetailsBinding
import com.example.foodium.viewmodel.RecipeDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.jsoup.Jsoup

@AndroidEntryPoint
class RecipeDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeDetailsBinding

    private val recipeArgs by navArgs<RecipeDetailsActivityArgs>()

    private val viewModel by viewModels<RecipeDetailsViewModel>()

    private val ingredientAdapter by lazy { IngredientAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        updateUI(recipeArgs.recipe)

        binding.textLink.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(recipeArgs.recipe.sourceUrl)))
        }

        ingredientAdapter.submitList(recipeArgs.recipe.extendedIngredients)

    }

    private fun updateUI(recipeEntity: RecipeEntity) {

        binding.apply {

            // title
            textRecipeTitle.text = recipeEntity.title

            // description
            textDescription.text = Jsoup.parse(recipeEntity.summary).text()

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

            // Ingredients count
            txtIngredientsCount.text = "Ingredients (${recipeEntity.extendedIngredients?.size})"

            recyclerViewIngredient.apply {
                layoutManager = LinearLayoutManager(
                    this@RecipeDetailsActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )

                setHasFixedSize(false)
                adapter = ingredientAdapter

            }

        }

    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        try {
            if (recipeArgs.recipe.saved) {
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
                shareRecipe()
                true
            }

            // Insert/Delete Recipe
            else -> {

                try {

                    // If already saved
                    if (recipeArgs.recipe.saved) {
                        viewModel.deleteRecipe(recipeArgs.recipe)
                        recipeArgs.recipe.saved = false
                        changeMenuIcon(item, R.drawable.ic_favorite_hollow)
                    }

                    // if not saved
                    else {
                        viewModel.insertRecipe(recipeArgs.recipe)
                        recipeArgs.recipe.saved = true
                        changeMenuIcon(item, R.drawable.ic_favorite_solid)
                    }

                    setRefreshQuery()

                } catch (e: Exception) {

                }

                true
            }
        }
    }

    private fun setRefreshQuery() = viewModel.setRefreshQuery(true)

    private fun shareRecipe() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Recipe URL")
            putExtra(Intent.EXTRA_TEXT, recipeArgs.recipe.sourceUrl)
            startActivity(this)
        }
    }

    private fun changeMenuIcon(item: MenuItem, icon: Int) = item.setIcon(icon)

}