package com.dicoding.core.data.local.preferences.utils

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

private const val USER_PREFERENCES = "user_preferences"
val Context.userPreferences by preferencesDataStore(
    name = USER_PREFERENCES
)

object PreferencesKeys {
    val IS_DARK_MODE_ENCRYPTED = stringPreferencesKey("is_dark_mode_encrypted")
}
