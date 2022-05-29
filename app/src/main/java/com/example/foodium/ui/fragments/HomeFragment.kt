package com.example.foodium.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodium.adapters.Category
import com.example.foodium.adapters.CategoryAdapter
import com.example.foodium.adapters.RecipeAdapter
import com.example.foodium.databinding.FragmentHomeBinding
import com.example.foodium.utils.Constants
import com.example.foodium.utils.Constants.APPETIZER
import com.example.foodium.utils.Constants.BEVERAGE
import com.example.foodium.utils.Constants.BREAD
import com.example.foodium.utils.Constants.BREAKFAST
import com.example.foodium.utils.Constants.DESSERT
import com.example.foodium.utils.Constants.MAIN_COURSE
import com.example.foodium.utils.Constants.SALAD
import com.example.foodium.utils.Constants.SAUCE
import com.example.foodium.utils.Constants.SIDE_DISH
import com.example.foodium.utils.Constants.SNACK
import com.example.foodium.utils.Constants.SOUP
import com.example.foodium.utils.Constants.categoryList
import com.example.foodium.utils.Resource
import com.example.foodium.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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

        val categoryAdapter = CategoryAdapter {
            Toast.makeText(requireContext(), it.title, Toast.LENGTH_SHORT).show()
        }

        val recipeAdapter = RecipeAdapter(
            saveRecipe = { recipe ->
                viewModel.saveRecipe(recipe)
            },
            deleteRecipe = { recipe ->
                viewModel.deleteRecipe(recipe)
            },
            onRecipeClick = { recipe ->
                findNavController()
                    .navigate(
                        HomeFragmentDirections.actionHomeFragmentToRecipeDetailsActivity(
                            recipe
                        )
                    )
            }
        )

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

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.recipesList.collectLatest {
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

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSavedRecipesFragment())
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}