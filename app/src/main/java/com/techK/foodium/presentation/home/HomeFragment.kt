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
import com.techK.foodium.R
import com.techK.foodium.data.NetworkObserver
import com.techK.foodium.databinding.FragmentHomeBinding
import com.techK.foodium.domain.utils.ExtensionFunctions.getColorRes
import com.techK.foodium.domain.utils.ExtensionFunctions.hide
import com.techK.foodium.domain.utils.ExtensionFunctions.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()

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