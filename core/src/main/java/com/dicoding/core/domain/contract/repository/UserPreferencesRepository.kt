package com.dicoding.core.domain.contract.repository

import com.dicoding.core.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    val userPreferencesFlow: Flow<UserPreferences>

    suspend fun updateDarkMode(isDarkMode: Boolean?)

}