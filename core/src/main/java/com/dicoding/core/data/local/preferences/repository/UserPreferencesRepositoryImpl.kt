package com.dicoding.core.data.local.preferences.repository

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.*
import com.dicoding.core.data.local.preferences.utils.EncryptionHelper
import com.dicoding.core.data.local.preferences.utils.PreferencesKeys
import com.dicoding.core.domain.contract.repository.UserPreferencesRepository
import com.dicoding.core.domain.model.UserPreferences
import kotlinx.coroutines.flow.*
import javax.crypto.SecretKey

class UserPreferencesRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
    private val secretKey: SecretKey
) : UserPreferencesRepository {

    override val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val encryptedDarkMode = preferences[PreferencesKeys.IS_DARK_MODE_ENCRYPTED]
            val isDarkMode = encryptedDarkMode?.let {
                EncryptionHelper.decryptData(it, secretKey).toBoolean()
            } ?: false

            UserPreferences(isDarkMode = isDarkMode)
        }

    override suspend fun updateDarkMode(isDarkMode: Boolean?) {
        dataStore.edit { preferences ->
            if (isDarkMode == null) {
                preferences.remove(PreferencesKeys.IS_DARK_MODE_ENCRYPTED)
            } else {
                val encryptedValue = EncryptionHelper.encryptData(isDarkMode.toString(), secretKey)
                preferences[PreferencesKeys.IS_DARK_MODE_ENCRYPTED] = encryptedValue
            }
        }
    }
}
