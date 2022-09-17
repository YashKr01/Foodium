package com.techK.foodium.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techK.foodium.data.NetworkObserver
import com.techK.foodium.data.response.Result
import com.techK.foodium.domain.usecases.GetRecipesUseCase
import com.techK.foodium.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRecipesUseCase: GetRecipesUseCase,
    private val networkObserver: NetworkObserver,
) : ViewModel() {

    private val _recipes = MutableStateFlow<Resource<List<Result>>>(Resource.Loading())
    val recipes get() = _recipes.asStateFlow()

    private val _connection = MutableStateFlow(true)
    val connection get() = _connection.asStateFlow()

    init {
        getRecipes()
        observeConnection()
    }

    private fun getRecipes() = viewModelScope.launch {
        getRecipesUseCase.invoke().collect {
            _recipes.emit(it)
        }
    }

    private fun observeConnection() = viewModelScope.launch {
        networkObserver.observeConnection().collect {
            _connection.emit(it)
        }
    }

}