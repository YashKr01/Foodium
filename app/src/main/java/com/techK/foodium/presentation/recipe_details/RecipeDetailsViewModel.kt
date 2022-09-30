package com.techK.foodium.presentation.recipe_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techK.foodium.domain.usecases.datastore.SetRefreshQueryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val setRefreshQueryUseCase: SetRefreshQueryUseCase,
) : ViewModel() {

    fun setRefreshQuery(refresh: Boolean) = viewModelScope.launch {
        setRefreshQueryUseCase(refresh)
    }

}