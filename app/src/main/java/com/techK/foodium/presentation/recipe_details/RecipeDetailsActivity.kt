package com.techK.foodium.presentation.recipe_details

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.techK.foodium.R
import com.techK.foodium.databinding.ActivityRecipeDetailsBinding
import com.techK.foodium.presentation.adapters.list_adapters.IngredientAdapter
import com.techK.foodium.presentation.adapters.list_adapters.InstructionsAdapter

class RecipeDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeDetailsBinding

    private val recipeArgs by navArgs<RecipeDetailsActivityArgs>()
    private val viewModel by viewModels<RecipeDetailsViewModel>()

    private val ingredientAdapter by lazy { IngredientAdapter() }
    private val instructionAdapter by lazy { InstructionsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_details)

        setSupportActionBar(binding.toolbar)

        binding.recipe = recipeArgs.recipe

        binding.textLink.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(recipeArgs.recipe.sourceUrl)))
        }

        setupAdapters()

    }

    private fun setupAdapters() {

        val recipe = recipeArgs.recipe

        when (recipe.instructions.isEmpty()) {
            true -> {
                binding.apply {
                    txtInstructions.visibility = View.GONE
                    recyclerViewInstructions.visibility = View.GONE
                }
            }
            false -> {
                binding.recyclerViewInstructions.apply {
                    setHasFixedSize(false)
                    adapter = ingredientAdapter
                    recipe.instructions[0].steps?.let {
                        adapter = instructionAdapter
                        instructionAdapter.submitList(it)
                    }
                }
            }
        }

        when (recipe.ingredients.isNullOrEmpty()) {
            true -> {
                binding.apply {
                    txtIngredientsCount.visibility = View.GONE
                    recyclerViewIngredient.visibility = View.GONE
                }
            }
            false -> {
                binding.recyclerViewIngredient.apply {
                    setHasFixedSize(false)
                    adapter = ingredientAdapter
                    ingredientAdapter.submitList(recipe.ingredients)
                }
            }
        }

    }

    private fun shareRecipe() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Recipe URL")
            putExtra(Intent.EXTRA_TEXT, recipeArgs.recipe.sourceUrl)
            startActivity(this)
        }
    }

    private fun changeMenuIcon(item: MenuItem, icon: Int) {
        item.setIcon(icon)
    }

}