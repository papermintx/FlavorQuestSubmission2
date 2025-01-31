package com.dicoding.flavorquest.ui.presentation.splash


import androidx.lifecycle.ViewModel
import com.dicoding.core.domain.contract.usecase.InsertInitialMealUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val insertInitialMealUseCase: InsertInitialMealUseCase
): ViewModel() {

    private var job: Job? = null

    fun getMeal() {
        job?.cancel()
        job = CoroutineScope(Job()).launch {
            insertInitialMealUseCase()
        }
    }

}