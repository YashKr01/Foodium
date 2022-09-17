package com.techK.foodium.presentation.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.techK.foodium.R
import com.techK.foodium.data.NetworkObserver
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    @Inject
    lateinit var networkObserver: NetworkObserver

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            networkObserver.observeConnection().collect {
                when (it) {
                    true -> {
                        Log.d("OBSERVE", "onCreateView: $it")
                    }
                    false -> {
                        Log.d("OBSERVE", "onCreateView: $it")
                    }
                }
            }

        }


        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<FloatingActionButton>(R.id.floatingActionButton)?.setOnClickListener {
            val nav = HomeFragmentDirections.actionHomeFragmentToSavedRecipesFragment()
            findNavController().navigate(nav)
        }


    }

}