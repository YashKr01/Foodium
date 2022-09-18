package com.techK.foodium.presentation.home

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.techK.foodium.R
import com.techK.foodium.databinding.FragmentHomeBinding
import com.techK.foodium.domain.utils.Constants
import com.techK.foodium.domain.utils.ExtensionFunctions.getColorRes
import com.techK.foodium.domain.utils.ExtensionFunctions.hide
import com.techK.foodium.domain.utils.ExtensionFunctions.scale
import com.techK.foodium.domain.utils.ExtensionFunctions.show
import com.techK.foodium.domain.utils.Resource
import com.techK.foodium.presentation.adapters.list_adapters.CategoryAdapter
import com.techK.foodium.presentation.adapters.list_adapters.RecipeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var recipesAdapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        init()

    }

    private fun init() {

        binding.floatingActionButton.scale(1f, 1f, 500L, 300L)

        // category adapter
        categoryAdapter = CategoryAdapter(
            selectedPosition = viewModel.selectedCategory,
            onCategoryClick = { it, position ->
                if (position != viewModel.selectedCategory) {
                    viewModel.selectedCategory = position
                    // TODO : Implement Search
                }
            })

        // category recycler view
        binding.recyclerViewCategories.apply {
            setHasFixedSize(false)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
            categoryAdapter.submitList(Constants.categoryList)
        }

        // recipe adapter
        recipesAdapter = RecipeAdapter(
            saveRecipe = { recipe ->

            },
            deleteRecipe = { recipe ->

            },
            onRecipeClick = { recipe ->

            }
        )

        // recipes recycler view
        binding.recyclerViewRecipes.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recipesAdapter
        }

    }

    private fun setupObservers() {

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            // observe recipe
            viewModel.recipes.collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        recipesAdapter.submitList(result.data)
                    }
                    is Resource.Error -> {
                        // TODO : HANDLE ERROR
                    }
                    is Resource.Loading -> {
                        // TODO : HANDLE LOADING
                    }
                }
            }

        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            // observe connection
            viewModel.connection.collectLatest { connected ->
                when (connected) {
                    true -> hideNoConnectionLayout()
                    false -> showNoConnectionLayout()
                }
            }
        }

    }

    private fun showNoConnectionLayout() {
        binding.txtNetworkStatus.text =
            getString(R.string.text_no_connectivity)
        binding.networkStatusLayout.apply {
            show()
            setBackgroundColor(requireContext().getColorRes(R.color.colorStatusNotConnected))
        }
    }

    private fun hideNoConnectionLayout() {
        binding.txtNetworkStatus.text =
            getString(R.string.text_connectivity)
        binding.networkStatusLayout.apply {
            setBackgroundColor(requireContext().getColorRes(R.color.colorStatusConnected))
            animate()
                .alpha(1f)
                .setStartDelay(500L)
                .setDuration(1000L)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        hide()
                    }
                })
        }
    }

}