package com.techK.foodium.presentation.home

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.techK.foodium.R
import com.techK.foodium.databinding.FragmentHomeBinding
import com.techK.foodium.domain.entities.Recipe
import com.techK.foodium.utils.Constants
import com.techK.foodium.utils.ExtensionFunctions.getColorRes
import com.techK.foodium.utils.ExtensionFunctions.hide
import com.techK.foodium.utils.ExtensionFunctions.scale
import com.techK.foodium.utils.ExtensionFunctions.show
import com.techK.foodium.utils.Resource
import com.techK.foodium.presentation.BaseFragment
import com.techK.foodium.presentation.adapters.list_adapters.CategoryAdapter
import com.techK.foodium.presentation.adapters.list_adapters.RecipeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

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

    override fun init() {

        binding.floatingActionButton.scale(1f, 1f, 500L, 300L)

        setupAdapters()
        setupRecyclerViews()

        binding.floatingActionButton.setOnClickListener {
            val navigation = HomeFragmentDirections.actionHomeFragmentToSavedRecipesFragment()
            findNavController().navigate(navigation)
        }

    }

    override fun setupObservers() {

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.recipes.collectLatest { result ->
                when (result) {
                    is Resource.Success -> successState(result.data)
                    is Resource.Error -> errorState(result.message)
                    is Resource.Loading -> loadingState()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.connection.collectLatest { connected ->
                when (connected) {
                    true -> hideNoConnectionLayout()
                    false -> showNoConnectionLayout()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.refreshQuery.collect { refresh ->
                if (refresh) viewModel.refreshList(recipesAdapter.currentList)
                viewModel.setRefreshQuery(false)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.event.collectLatest { event ->
                when (event) {
                    is HomeEvent.ShowRecipeDeletedMessage -> showSnackBar(true)
                    is HomeEvent.ShowRecipeSavedMessage -> showSnackBar(false)
                    is HomeEvent.NavigateToDetailsScreen -> findNavController().navigate(
                        HomeFragmentDirections.actionHomeFragmentToRecipeDetailsActivity(event.recipe)
                    )
                }
            }
        }

    }

    private fun setupAdapters() {

        categoryAdapter = CategoryAdapter(
            selectedPosition = viewModel.selectedPosition.value,
            onCategoryClick = { category, position ->
                viewModel.setSelectedPosition(position)
                viewModel.setSelectedQuery(category)
                viewModel.searchRecipe(category)
            }
        )

        recipesAdapter = RecipeAdapter(
            saveRecipe = { recipe -> viewModel.saveRecipe(recipe) },
            deleteRecipe = { recipe -> viewModel.deleteRecipe(recipe) },
            onRecipeClick = { recipe -> viewModel.navigateToDetailsScreen(recipe) }
        )

    }

    private fun setupRecyclerViews() {
        binding.recyclerViewCategories.apply {
            setHasFixedSize(false)
            adapter = categoryAdapter
            categoryAdapter.submitList(Constants.categoryList)
        }

        binding.recyclerViewRecipes.apply {
            setHasFixedSize(false)
            itemAnimator = null
            adapter = recipesAdapter
        }
    }

    private fun showSnackBar(saved: Boolean) {
        when (saved) {
            true -> Snackbar.make(binding.root, "Recipe Deleted", Snackbar.LENGTH_SHORT).show()
            false -> Snackbar.make(binding.root, "Recipe Saved", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun errorState(message: String?) {
        binding.apply {
            Toast.makeText(requireContext(), "$message", Toast.LENGTH_SHORT).show()
            recyclerViewRecipes.visibility = View.GONE
            shimmerLayout.stopShimmer()
            shimmerLayout.visibility = View.GONE
        }
    }

    private fun successState(data: List<Recipe>?) {
        binding.apply {
            val animation =
                AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.recipe_layout_animation)
            recyclerViewRecipes.layoutManager?.scrollToPosition(0)
            recyclerViewRecipes.layoutAnimation = animation
            recyclerViewRecipes.visibility = View.VISIBLE
            shimmerLayout.stopShimmer()
            shimmerLayout.visibility = View.GONE
            recipesAdapter.submitList(data)
        }
    }

    private fun loadingState() {
        binding.apply {
            recyclerViewRecipes.visibility = View.GONE
            shimmerLayout.visibility = View.VISIBLE
            shimmerLayout.startShimmer()
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
        binding.txtNetworkStatus.text = getString(R.string.text_connectivity)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}