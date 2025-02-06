package com.dicoding.core.data.local.preferences.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import com.dicoding.core.data.local.preferences.utils.EncryptionHelper
import com.dicoding.core.data.local.preferences.utils.PreferencesKeys
import com.dicoding.core.domain.contract.repository.UserPreferencesRepository
import com.dicoding.core.domain.model.UserPreferences
import javax.crypto.SecretKey
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class UserPreferencesRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
    private val secretKey: SecretKey
) : UserPreferencesRepository {

    override val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e("UserPreferencesRepo", "Error reading preferences", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val encryptedDarkMode = preferences[PreferencesKeys.IS_DARK_MODE_ENCRYPTED]
            Log.d("UserPreferencesRepo", "Encrypted Dark Mode: $encryptedDarkMode")

            val isDarkMode = encryptedDarkMode?.let {
                try {
                    val decryptedValue = EncryptionHelper.decryptData(it, secretKey)
                    Log.d("UserPreferencesRepo", "Decrypted Dark Mode: $decryptedValue")

                    decryptedValue.toBooleanStrictOrNull() ?: false
                } catch (e: Exception) {
                    Log.e("UserPreferencesRepo", "Error decrypting dark mode preference", e)
                    false
                }
            } ?: false

            UserPreferences(isDarkMode = isDarkMode)
        }

    override suspend fun updateDarkMode(isDarkMode: Boolean?) {
        dataStore.edit { preferences ->
            if (isDarkMode == null) {
                preferences.remove(PreferencesKeys.IS_DARK_MODE_ENCRYPTED)
                Log.d("UserPreferencesRepo", "Dark mode preference removed")
            } else {
                try {
                    val encryptedValue = EncryptionHelper.encryptData(isDarkMode.toString(), secretKey)
                    preferences[PreferencesKeys.IS_DARK_MODE_ENCRYPTED] = encryptedValue
                    Log.d("UserPreferencesRepo", "Dark mode preference updated (Encrypted: $encryptedValue)")
                } catch (e: Exception) {
                    Log.e("UserPreferencesRepo", "Error encrypting dark mode preference", e)
                }
            }
        }
    }
}
