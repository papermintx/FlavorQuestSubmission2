package com.dicoding.flavorquest.ui.presentation.settings.viewmodel

sealed interface SettingEvent {
    data class UpdateThemeMode(
        val isDarkMode: Boolean
    ) : SettingEvent
}