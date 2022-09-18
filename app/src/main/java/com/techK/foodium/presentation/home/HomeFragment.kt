package com.techK.foodium.presentation.home

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.techK.foodium.R
import com.techK.foodium.databinding.FragmentHomeBinding
import com.techK.foodium.domain.utils.Constants
import com.techK.foodium.domain.utils.ExtensionFunctions.getColorRes
import com.techK.foodium.domain.utils.ExtensionFunctions.hide
import com.techK.foodium.domain.utils.ExtensionFunctions.show
import com.techK.foodium.presentation.adapters.CategoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        setupObservers()

    }

    private fun init() {

        categoryAdapter = CategoryAdapter(
            selectedPosition = viewModel.selectedCategory,
            onCategoryClick = { it, position ->
                if (position != viewModel.selectedCategory) {
                    viewModel.selectedCategory = position
                    // TODO : Implement Search
                }
            })

        binding.recyclerViewCategories.apply {
            setHasFixedSize(false)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
            categoryAdapter.submitList(Constants.categoryList)
        }

    }

    private fun setupObservers() {

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
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