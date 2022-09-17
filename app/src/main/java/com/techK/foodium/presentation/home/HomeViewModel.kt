package com.techK.foodium.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techK.foodium.data.response.Result
import com.techK.foodium.domain.usecases.GetRecipesUseCase
import com.techK.foodium.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRecipesUseCase: GetRecipesUseCase
) : ViewModel() {

    private val _recipes = MutableStateFlow<Resource<List<Result>>>(Resource.Loading())

    init {
        getRecipes()
    }

    private fun getRecipes() = viewModelScope.launch {
        getRecipesUseCase.invoke().collect {
            _recipes.emit(it)
        }
    }

}