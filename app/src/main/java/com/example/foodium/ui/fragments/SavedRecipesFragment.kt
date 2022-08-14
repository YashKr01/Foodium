package com.example.foodium.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodium.R
import com.example.foodium.adapters.SavedRecipeAdapter
import com.example.foodium.databinding.FragmentSavedRecipesBinding
import com.example.foodium.utils.SortOrder
import com.example.foodium.viewmodel.SavedRecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first

@AndroidEntryPoint
class SavedRecipesFragment : Fragment() {

    private var _binding: FragmentSavedRecipesBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<SavedRecipesViewModel>()
    private lateinit var savedRecipesAdapter: SavedRecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedRecipesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)

        savedRecipesAdapter = SavedRecipeAdapter(
            deleteRecipe = { recipeEntity ->
                viewModel.deleteRecipe(recipeEntity)
                setRefreshQuery()
            }, onClick = { recipe ->
                findNavController()
                    .navigate(
                        SavedRecipesFragmentDirections
                            .actionSavedRecipesFragmentToRecipeDetailsActivity(recipe)
                    )
            })

        binding.recyclerViewSavedRecipes.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = savedRecipesAdapter
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.savedRecipes.collect {
                savedRecipesAdapter.submitList(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.sortOrder.collect { sortOrder ->
                viewModel.getSavedListByOrder(sortOrder)
            }
        }

    }

    private fun setRefreshQuery() = viewModel.setRefreshQuery(true)

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.saved_recipes_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            when(viewModel.sortOrder.first()) {
                SortOrder.BY_NAME -> menu.findItem(R.id.menu_sort_by_name).isChecked = true
                SortOrder.BY_LIKES -> menu.findItem(R.id.menu_sort_by_likes).isChecked = true
                SortOrder.BY_TIME -> menu.findItem(R.id.menu_sort_by_time).isChecked = true
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_delete_all -> {
                showAlertDialog()
            }
            R.id.menu_sort_by_name -> {
                viewModel.setSortOrder(SortOrder.BY_NAME)
                item.isChecked = true
            }
            R.id.menu_sort_by_likes -> {
                viewModel.setSortOrder(SortOrder.BY_LIKES)
                item.isChecked = true
            }
            R.id.menu_sort_by_time -> {
                viewModel.setSortOrder(SortOrder.BY_TIME)
                item.isChecked = true
            }
        }
        return true
    }

    private fun showAlertDialog() {
        val dialog = AlertDialog.Builder(requireActivity())
        dialog.apply {
            setMessage("Would you like to delete all saved recipes?")
            setPositiveButton("OK") { _, _ ->
                viewModel.deleteAllRecipe()
                viewModel.setRefreshQuery(true)
            }
            setNegativeButton("Cancel") { _, _ ->
            }
        }

        dialog.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}