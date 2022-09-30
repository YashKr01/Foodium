package com.techK.foodium.presentation.recipe_details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.navigation.navArgs
import com.techK.foodium.R
import com.techK.foodium.databinding.ActivityRecipeDetailsBinding
import com.techK.foodium.presentation.adapters.list_adapters.IngredientAdapter
import com.techK.foodium.presentation.adapters.list_adapters.InstructionsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

        addMenuProvider(menuProvider)

        setupAdapters()

    }

    private val menuProvider = object : MenuProvider {
        override fun onPrepareMenu(menu: Menu) {
            super.onPrepareMenu(menu)
            if (recipeArgs.recipe.saved) {
                val icon = menu.findItem(R.id.menu_save)
                changeMenuIcon(icon, R.drawable.ic_favorite_solid)
            }
        }

        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.recipe_details_menu, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            return when (menuItem.itemId) {
                R.id.menu_share -> {
                    shareRecipe()
                    true
                }
                else -> {
                    when (recipeArgs.recipe.saved) {
                        true -> {
                            viewModel.deleteRecipe(recipeArgs.recipe)
                            recipeArgs.recipe.saved = false
                            changeMenuIcon(menuItem, R.drawable.ic_favorite_hollow)
                        }
                        false -> {
                            viewModel.insertRecipe(recipeArgs.recipe)
                            recipeArgs.recipe.saved = true
                            changeMenuIcon(menuItem, R.drawable.ic_favorite_solid)
                        }
                    }

                    viewModel.setRefreshQuery(true)
                    true
                }
            }
        }
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