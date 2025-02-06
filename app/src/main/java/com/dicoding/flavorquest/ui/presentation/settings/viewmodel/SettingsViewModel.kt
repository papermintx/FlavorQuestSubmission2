package com.dicoding.flavorquest.ui.presentation.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.core.domain.contract.usecase.GetThemeModeUseCase
import com.dicoding.core.domain.contract.usecase.UpdateThemeModeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getThemeModeUseCase: GetThemeModeUseCase,
    private val updateThemeModeUseCase: UpdateThemeModeUseCase
): ViewModel() {

    private val _themeMode = MutableStateFlow(false)
    val themeMode = _themeMode.asStateFlow()

    private var getThemeModeJob: Job? = null
    private var updateThemeModeJob: Job? = null

    init {
        getThemeMode()
    }

    fun onEvent(event: SettingEvent) {
        when(event) {
            is SettingEvent.UpdateThemeMode -> updateThemeMode(event.isDarkMode)
            SettingEvent.GetThemeMode -> getThemeMode()
        }
    }

    private fun getThemeMode() {
        getThemeModeJob?.cancel()

        getThemeModeJob = viewModelScope.launch {
            getThemeModeUseCase().collect { themeMode ->
                _themeMode.value = themeMode
            }
        }
    }

    private fun updateThemeMode(isDarkMode: Boolean) {
        updateThemeModeJob?.cancel()

        updateThemeModeJob = viewModelScope.launch {
            updateThemeModeUseCase(isDarkMode)
        }
    }
}
