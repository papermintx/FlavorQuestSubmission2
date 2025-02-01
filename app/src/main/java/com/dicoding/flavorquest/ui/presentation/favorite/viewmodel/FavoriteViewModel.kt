package com.dicoding.flavorquest.ui.presentation.favorite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.core.common.State
import com.dicoding.core.domain.contract.usecase.DeleteMealFavoriteUseCase
import com.dicoding.core.domain.contract.usecase.GetAllFavoriteUseCase
import com.dicoding.core.domain.model.Meal
import com.dicoding.flavorquest.common.StateUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getAllFavoriteUseCase: GetAllFavoriteUseCase,
    private val deleteMealFavoriteUseCase: DeleteMealFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<StateUi<List<Meal>>>(StateUi())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getAllFavoriteUseCase().collect { state ->
                when (state) {
                    State.Empty -> {
                        _state.update {
                            it.copy(isLoading = false, error = Pair(false, ""))
                        }
                    }

                    is State.Error -> {
                        _state.update {
                            it.copy(isLoading = false, error = Pair(true, state.message))
                        }
                    }

                    State.Loading -> {
                        _state.update {
                            it.copy(isLoading = true, error = Pair(false, ""))
                        }
                    }

                    is State.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                data = state.data.filterNotNull(),
                                error = Pair(false, "")
                            )
                        }
                    }
                }
            }
        }
    }


    fun deleteFavorite(meal: Meal) {
        viewModelScope.launch {
            deleteMealFavoriteUseCase(meal)
        }
    }
}
