package com.example.foodium.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.foodium.databinding.FragmentHomeBinding
import com.example.foodium.utils.Resource
import com.example.foodium.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

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

        viewModel.recipesList.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    Log.d("STATUS", "onViewCreated: LOADING")
                }
                is Resource.Error -> {
                    Log.d("STATUS", "onViewCreated: ERROR")
                }
                is Resource.Success -> {
                    Log.d("STATUS", "onViewCreated: SUCCESS")
                    Log.d("STATUS", "onViewCreated: ${result.data}")
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}