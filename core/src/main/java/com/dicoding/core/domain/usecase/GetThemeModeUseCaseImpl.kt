package com.dicoding.core.domain.usecase

import android.util.Log
import com.dicoding.core.domain.contract.repository.UserPreferencesRepository
import com.dicoding.core.domain.contract.usecase.GetThemeModeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetThemeModeUseCaseImpl (
    private val userPreferencesRepository: UserPreferencesRepository
): GetThemeModeUseCase {
    override suspend fun invoke(): Flow<Boolean> = flow {
        try {
            userPreferencesRepository.userPreferencesFlow.collect { userPreferences ->
                if (userPreferences.isDarkMode != null) {
                    emit(userPreferences.isDarkMode)
                } else {
                    emit(false)
                }
            }
        } catch (e: Exception) {
            Log.e("GetThemeModeUseCaseImpl", e.message.toString())
            emit(false)
        }
    }.flowOn(Dispatchers.IO)
}