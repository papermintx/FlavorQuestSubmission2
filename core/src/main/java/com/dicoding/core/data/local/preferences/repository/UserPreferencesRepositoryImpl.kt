package com.dicoding.core.data.local.preferences.repository

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.dicoding.core.data.local.preferences.utils.PreferencesKeys
import com.dicoding.core.domain.contract.repository.UserPreferencesRepository
import com.dicoding.core.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class UserPreferencesRepositoryImpl(
    private val dataStore: DataStore<Preferences>
): UserPreferencesRepository {
    override val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch{ exception ->
            if (exception is IOException){
                emit(emptyPreferences())
            }else{
                throw exception
            }
        }
        .map{ preferences ->
            val isDarkMode = preferences[PreferencesKeys.IS_DARK_MODE] == true

            UserPreferences(
                isDarkMode = isDarkMode,
            )
        }

    override suspend fun updateDarkMode(isDarkMode: Boolean?) {
        dataStore.edit { preferences ->
            if (isDarkMode == null) {
                preferences.remove(PreferencesKeys.IS_DARK_MODE)
            } else {
                preferences[PreferencesKeys.IS_DARK_MODE] = isDarkMode
            }
        }
    }
}