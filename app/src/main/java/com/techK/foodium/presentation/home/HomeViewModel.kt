package com.techK.foodium.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techK.foodium.data.NetworkObserver
import com.techK.foodium.domain.entities.Category
import com.techK.foodium.domain.entities.Recipe
import com.techK.foodium.domain.usecases.database.DeleteRecipeUseCase
import com.techK.foodium.domain.usecases.database.RefreshListUseCase
import com.techK.foodium.domain.usecases.database.SaveRecipeUseCase
import com.techK.foodium.domain.usecases.datastore.GetRefreshQueryUseCase
import com.techK.foodium.domain.usecases.datastore.SetRefreshQueryUseCase
import com.techK.foodium.domain.usecases.network.GetRecipesUseCase
import com.techK.foodium.domain.usecases.network.SearchRecipesUseCase
import com.techK.foodium.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRecipesUseCase: GetRecipesUseCase,
    private val saveRecipeUseCase: SaveRecipeUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase,
    private val networkObserver: NetworkObserver,
    private val getRefreshQueryUseCase: GetRefreshQueryUseCase,
    private val setRefreshQueryUseCase: SetRefreshQueryUseCase,
    private val refreshListUseCase: RefreshListUseCase,
    private val searchRecipesUseCase: SearchRecipesUseCase,
) : ViewModel() {

    private val _recipes = MutableStateFlow<Resource<List<Recipe>>>(Resource.Loading())
    val recipes get() = _recipes.asStateFlow()

    private val _connection = MutableStateFlow(true)
    val connection get() = _connection.asStateFlow()

    private val _refreshQuery = MutableStateFlow(false)
    val refreshQuery get() = _refreshQuery.asStateFlow()

    private val _selectedPosition = MutableStateFlow(0)
    val selectedPosition get() = _selectedPosition.asStateFlow()

    private val _query = MutableStateFlow(Category(""))
    val query get() = _query.asStateFlow()

    init {
        getRecipes()
        observeConnection()
        getRefreshQuery()
    }

    private fun getRecipes() = viewModelScope.launch {
        getRecipesUseCase().collect {
            _recipes.emit(it)
        }
    }

    private fun observeConnection() = viewModelScope.launch {
        networkObserver.observeConnection().collect {
            _connection.emit(it)
        }
    }

    fun saveRecipe(recipe: Recipe) = viewModelScope.launch {
        saveRecipeUseCase(recipe)
    }

    fun deleteRecipe(recipe: Recipe) = viewModelScope.launch {
        deleteRecipeUseCase(recipe)
    }

    fun setRefreshQuery(refresh: Boolean) = viewModelScope.launch {
        setRefreshQueryUseCase(refresh)
    }

    private fun getRefreshQuery() = viewModelScope.launch {
        getRefreshQueryUseCase().collect { _refreshQuery.emit(it) }
    }

    fun refreshList(currentList: MutableList<Recipe>) = viewModelScope.launch {
        refreshListUseCase(currentList).collect {
            _recipes.emit(Resource.Success(data = it))
        }
    }

    fun setSelectedPosition(position: Int) = viewModelScope.launch {
        _selectedPosition.emit(position)
    }

    fun setSelectedQuery(query: Category) = viewModelScope.launch {
        _query.emit(query)
    }

    fun searchRecipe(category: Category) = viewModelScope.launch {
        searchRecipesUseCase(category.title).collect {
            _recipes.emit(it)
        }
    }

}