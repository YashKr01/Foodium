package com.techK.foodium.presentation.saved_recipes

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.techK.foodium.R
import com.techK.foodium.databinding.FragmentSavedRecipesBinding
import com.techK.foodium.domain.entities.Recipe
import com.techK.foodium.domain.enums.SortOrder
import com.techK.foodium.presentation.BaseFragment
import com.techK.foodium.presentation.adapters.list_adapters.SavedRecipeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SavedRecipesFragment : BaseFragment() {

    private var _binding: FragmentSavedRecipesBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<SavedRecipesViewModel>()
    private lateinit var savedRecipesAdapter: SavedRecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSavedRecipesBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val menuProvider = object : MenuProvider {

        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.saved_recipes_menu, menu)
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.sortOrder.collectLatest { order ->
                    when (order) {
                        SortOrder.BY_NAME -> checkMenuItem(menu, R.id.menu_sort_by_name)
                        SortOrder.BY_LIKES -> checkMenuItem(menu, R.id.menu_sort_by_likes)
                        SortOrder.BY_TIME -> checkMenuItem(menu, R.id.menu_sort_by_time)
                        SortOrder.NONE -> Unit
                    }
                    viewModel.getSavedListByOrder(order)
                }
            }
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            when (menuItem.itemId) {
                R.id.menu_delete_all -> showAlertDialog()
                R.id.menu_sort_by_name -> viewModel.setSortOrder(SortOrder.BY_NAME)
                R.id.menu_sort_by_likes -> viewModel.setSortOrder(SortOrder.BY_LIKES)
                R.id.menu_sort_by_time -> viewModel.setSortOrder(SortOrder.BY_TIME)
            }
            return true
        }
    }

    override fun init() {

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)

        requireActivity().addMenuProvider(menuProvider, viewLifecycleOwner, Lifecycle.State.RESUMED)

        savedRecipesAdapter = SavedRecipeAdapter(
            deleteRecipe = { recipe ->
                viewModel.deleteRecipe(recipe)
                showSnackBar(recipe)
                viewModel.setRefreshQuery(true)
            },
            onRecipeClick = { recipe ->
                val navDirection = SavedRecipesFragmentDirections
                    .actionSavedRecipesFragmentToRecipeDetailsActivity(recipe)
                findNavController().navigate(navDirection)
            }
        )

        binding.recyclerViewSavedRecipes.apply {
            setHasFixedSize(false)
            itemAnimator = null
            layoutManager = LinearLayoutManager(requireContext())
            adapter = savedRecipesAdapter
        }

    }

    override fun setupObservers() {

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.savedRecipes.collectLatest {
                savedRecipesAdapter.submitList(it)
            }
        }

    }

    private fun showSnackBar(recipe: Recipe) {
        Snackbar.make(
            binding.root,
            "Recipe Deleted",
            Snackbar.LENGTH_SHORT
        ).setAction("UNDO") {
            viewModel.saveRecipe(recipe)
        }.show()
    }

    private fun showAlertDialog() {
        val dialog = AlertDialog.Builder(requireActivity())
        dialog.apply {
            setMessage(getString(R.string.delete_confirm_message))
            setPositiveButton(getString(R.string.text_ok)) { _, _ ->
                viewModel.deleteAllRecipe()
                viewModel.setRefreshQuery(true)
            }
            setNegativeButton(getString(R.string.text_cancel)) { _, _ ->

            }
        }

        dialog.create().show()
    }

    private fun checkMenuItem(menu: Menu, id: Int) {
        menu.findItem(id).isChecked = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}