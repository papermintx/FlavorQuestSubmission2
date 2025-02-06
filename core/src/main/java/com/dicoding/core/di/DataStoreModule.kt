package com.dicoding.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.dicoding.core.data.local.preferences.repository.UserPreferencesRepositoryImpl
import com.dicoding.core.data.local.preferences.utils.KeyStoreHelper
import com.dicoding.core.data.local.preferences.utils.userPreferences
import com.dicoding.core.domain.contract.repository.UserPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.crypto.SecretKey
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.userPreferences
    }

    @Provides
    @Singleton
    fun provideSecretKey(): SecretKey {
        return KeyStoreHelper.getOrCreateSecretKey()
    }

    @Singleton
    @Provides
    fun provideUserPreferencesRepository(
        dataStore: DataStore<Preferences>,
        secretKey: SecretKey
    ): UserPreferencesRepository {
        return UserPreferencesRepositoryImpl(dataStore,  secretKey)
    }
}