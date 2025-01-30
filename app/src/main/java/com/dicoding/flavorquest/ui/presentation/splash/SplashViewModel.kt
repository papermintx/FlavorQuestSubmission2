package com.dicoding.flavorquest.ui.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.core.domain.contract.usecase.InsertInitialMeal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val insertInitialMeal: InsertInitialMeal
): ViewModel() {

    fun getMeal() {
        viewModelScope.launch {
            insertInitialMeal()
        }
    }
}