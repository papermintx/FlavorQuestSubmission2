package com.dicoding.flavorquest.ui.presentation.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.core.domain.contract.usecase.GetThemeModeUseCase
import com.dicoding.core.domain.contract.usecase.UpdateThemeModeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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

    init {
        getThemeMode()
    }

    fun onEvent(event: SettingEvent) {
        when(event) {
            is SettingEvent.UpdateThemeMode -> updateThemeMode(event.isDarkMode)
        }
    }

    private fun getThemeMode() = viewModelScope.launch {
        getThemeModeUseCase().collect { themeMode ->
            _themeMode.value = themeMode
        }
    }

    private fun updateThemeMode(isDarkMode: Boolean) = viewModelScope.launch {
        updateThemeModeUseCase(isDarkMode)
    }
}