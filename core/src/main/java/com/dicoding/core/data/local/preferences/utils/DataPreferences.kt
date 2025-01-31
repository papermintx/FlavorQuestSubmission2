package com.dicoding.core.data.local.preferences.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

private const val USER_PREFERENCES = "user_preferences"
val Context.userPreferences by preferencesDataStore(
    name = USER_PREFERENCES
)

object PreferencesKeys {
    val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
}