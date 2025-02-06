package com.dicoding.favorite.ui.detail

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.core.common.State
import com.dicoding.core.data.mapper.toMeal
import com.dicoding.core.domain.contract.usecase.GetMealDetailUseCase
import com.dicoding.core.domain.contract.usecase.InsertFavoriteMealUseCase
import com.dicoding.core.domain.model.Meal
import com.dicoding.core.domain.model.MealDetail
import com.dicoding.flavorquest.common.StateUi
import com.dicoding.flavorquest.ui.presentation.detail.viewmodel.DetailEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
//
//@HiltViewModel
//class DetailMealViewModel @Inject constructor(
//    private val getMealDetailUseCase: GetMealDetailUseCase,
//    private val insertFavoriteMealUseCase: InsertFavoriteMealUseCase
//): ViewModel(), IDetailMealViewModel {
//
//    private val _state = MutableStateFlow<StateUi<MealDetail>>(StateUi())
//    override val state: StateFlow<StateUi<MealDetail>> = _state.asStateFlow()
//
//
//    override fun onEvent(event: DetailEvent) {
//        when(event) {
//            is DetailEvent.GetDetail -> getDetailMeal(event.id)
//            DetailEvent.ResetError -> _state.update {
//                it.copy(
//                    error = Pair(false, "")
//                )
//            }
//            is DetailEvent.SetFavorite -> {
//                val meal = event.meal.toMeal()
//                insertFavoriteMeal(meal, context = event.context)
//            }
//        }
//    }
//
//    private fun insertFavoriteMeal(meal: Meal, context: Context) {
//        viewModelScope.launch {
//            try {
//                insertFavoriteMealUseCase(meal)
//                Toast.makeText(context, "Added to favorite successfully", Toast.LENGTH_SHORT).show()
//            } catch (e: Exception) {
//                _state.update {
//                    it.copy(
//                        error = Pair(true, e.message ?: "An error occurred")
//                    )
//                }
//            }
//        }
//    }
//
//
//    private fun getDetailMeal(id: String) {
//        viewModelScope.launch {
//            try {
//                getMealDetailUseCase(id).collect{ state ->
//                    when(state){
//                        State.Empty -> {
//                            _state.update {
//                                it.copy(
//                                    isLoading = false,
//                                    error = Pair(false, "")
//                                )
//                            }
//                        }
//                        is State.Error -> {
//                            _state.update {
//                                it.copy(
//                                    isLoading = false,
//                                    error = Pair(true, state.message)
//                                )
//                            }
//                        }
//                        State.Loading -> {
//                            _state.update {
//                                it.copy(
//                                    isLoading = true,
//                                    error = Pair(false, "")
//                                )
//                            }
//                        }
//                        is State.Success -> {
//                            _state.update {
//                                it.copy(
//                                    isLoading = false,
//                                    data = state.data,
//                                    error = Pair(false, "")
//                                )
//                            }
//                        }
//                    }
//
//                }
//
//            } catch (e: Exception){
//                _state.update {
//                    it.copy(
//                        isLoading = false,
//                        error = Pair(true, e.message ?: "An error occurred")
//                    )
//                }
//            }
//
//        }
//    }
//}