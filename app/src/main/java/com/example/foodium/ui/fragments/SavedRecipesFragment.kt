package com.example.foodium.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodium.adapters.SavedRecipeAdapter
import com.example.foodium.databinding.FragmentSavedRecipesBinding
import com.example.foodium.utils.Constants
import com.example.foodium.utils.Constants.IS_CHANGED
import com.example.foodium.viewmodel.SavedRecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SavedRecipesFragment : Fragment() {

    private var _binding: FragmentSavedRecipesBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<SavedRecipesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedRecipesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val savedRecipesAdapter = SavedRecipeAdapter(
            deleteRecipe = { recipeEntity ->
                viewModel.deleteRecipe(recipeEntity)
                findNavController().previousBackStackEntry?.savedStateHandle?.set(IS_CHANGED, true)
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

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}