package com.dicoding.flavorquest.ui.presentation.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.core.common.State
import com.dicoding.core.domain.contract.usecase.GetAllMealInitialUseCase
import com.dicoding.core.domain.contract.usecase.SearchMealUseCase
import com.dicoding.core.domain.model.Meal
import com.dicoding.flavorquest.common.StateUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val searchMealUseCase: SearchMealUseCase,
    private val getAllInitialMealUseCase: GetAllMealInitialUseCase
) : ViewModel() {

    private val _searchState = MutableStateFlow<StateUi<List<Meal>>>(StateUi())
    val searchState = _searchState.asStateFlow()

    private var job: Job? = null

    init {
        fetchInitialMeals()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.Search -> {
                Log.d(TAG, "onEvent: ${event.query}")
                searchMeal(event.query)
            }
            is HomeEvent.ResetError -> {
                resetSearchError()
            }
        }
    }

    private fun fetchInitialMeals() {
        job?.cancel()
        job = viewModelScope.launch {
            getAllInitialMealUseCase().collect { state ->
                when (state) {
                    is State.Loading -> {
                        _searchState.update { it.copy(isLoading = true, error = Pair(false, "")) }
                    }
                    is State.Success -> {
                        _searchState.update {
                            it.copy(
                                isLoading = false,
                                data = state.data.filterNotNull(),
                                error = Pair(false, "")
                            )
                        }
                    }
                    is State.Error -> {
                        _searchState.update { it.copy(isLoading = false, error = Pair(true, state.message)) }
                    }
                    State.Empty -> {
                        _searchState.update {
                            it.copy(
                                isLoading = false,
                                error = Pair(false, ""),
                                data = emptyList()
                            )
                        }
                    }
                }
            }
        }
    }
    private fun resetSearchError() {
        _searchState.update { it.copy(error = Pair(false, "")) }
    }

    private fun searchMeal(query: String) {
        job?.cancel()
        job = viewModelScope.launch {
            searchMealUseCase(query).collect { state ->
                when (state) {
                    is State.Loading -> {
                        _searchState.update { it.copy(isLoading = true, error = Pair(false, "")) }
                    }
                    is State.Success -> {
                        _searchState.update { it.copy(isLoading = false, data = state.data, error = Pair(false, "")) }
                    }
                    is State.Error -> {
                        _searchState.update { it.copy(isLoading = false, error = Pair(true, state.message)) }
                    }
                    State.Empty -> {
                        _searchState.update {
                            it.copy(
                                isLoading = false,
                                error = Pair(false, ""),
                                data = emptyList()
                            )
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val TAG = "HomeViewModel"
    }
}
