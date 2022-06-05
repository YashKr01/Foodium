package com.example.foodium.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodium.adapters.CategoryAdapter
import com.example.foodium.adapters.RecipeAdapter
import com.example.foodium.data.database.model.RecipeEntity
import com.example.foodium.databinding.FragmentHomeBinding
import com.example.foodium.utils.Constants.categoryList
import com.example.foodium.utils.Resource
import com.example.foodium.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var recipeAdapter: RecipeAdapter

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryAdapter = initCategoryAdapter()
        initRecipeAdapter()

        setupRecyclerViews(recipeAdapter, categoryAdapter)

        registerObservers(recipeAdapter)

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSavedRecipesFragment())
        }

    }

    private fun initCategoryAdapter() = CategoryAdapter(
        selectedPosition = viewModel.selectedCategory,
        onCategoryClick = { it, position ->
            if (position != viewModel.selectedCategory) {
                viewModel.selectedCategory = position
                viewModel.searchRecipes(it.title)
            }
        })

    private fun initRecipeAdapter() {
        recipeAdapter = RecipeAdapter(saveRecipe = { recipe -> viewModel.saveRecipe(recipe) },
            deleteRecipe = { recipe -> viewModel.deleteRecipe(recipe) },
            onRecipeClick = { recipe ->
                findNavController()
                    .navigate(
                        HomeFragmentDirections.actionHomeFragmentToRecipeDetailsActivity(recipe)
                    )
            })
    }

    private fun registerObservers(recipeAdapter: RecipeAdapter) {

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.recipesList.asStateFlow().collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        Log.d("STATUS", "onViewCreated: LOADING")
                    }
                    is Resource.Error -> {
                        Log.d("STATUS", "onViewCreated: ERROR")
                    }
                    is Resource.Success -> {
                        recipeAdapter.submitList(it.data)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.refreshList.distinctUntilChanged().collectLatest { refresh ->
                if (refresh) refreshList(recipeAdapter.currentList)
                viewModel.setRefreshQuery()
            }
        }

    }

    private suspend fun refreshList(currentList: MutableList<RecipeEntity>) {

        val savedList = viewModel.getSavedList().firstOrNull()

        savedList?.let {

            val newList = currentList.map { recipe ->

                val isSaved = it.any { savedRecipe ->
                    savedRecipe.recipeId == recipe.recipeId
                }

                RecipeEntity(
                    instructions = recipe.instructions,
                    aggregateLikes = recipe.aggregateLikes,
                    cheap = recipe.cheap,
                    dairyFree = recipe.dairyFree,
                    extendedIngredients = recipe.extendedIngredients,
                    glutenFree = recipe.glutenFree,
                    recipeId = recipe.recipeId,
                    image = recipe.image,
                    readyInMinutes = recipe.readyInMinutes,
                    sourceName = recipe.sourceName,
                    sourceUrl = recipe.sourceUrl,
                    summary = recipe.summary,
                    title = recipe.title,
                    vegan = recipe.vegan,
                    vegetarian = recipe.vegetarian,
                    veryHealthy = recipe.veryHealthy,
                    popular = recipe.popular,
                    healthScore = recipe.healthScore,
                    saved = isSaved
                )

            }

            viewModel.updateList(newList)

        }

    }

    private fun setupRecyclerViews(
        recipeAdapter: RecipeAdapter,
        categoryAdapter: CategoryAdapter
    ) {
        binding.recyclerViewRecipes.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recipeAdapter
        }

        binding.recyclerViewCategories.apply {
            setHasFixedSize(false)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
            categoryAdapter.submitList(categoryList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}