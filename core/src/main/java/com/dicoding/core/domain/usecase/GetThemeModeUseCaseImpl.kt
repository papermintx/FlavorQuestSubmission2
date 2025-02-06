package com.dicoding.core.domain.usecase

import android.util.Log
import com.dicoding.core.domain.contract.repository.UserPreferencesRepository
import com.dicoding.core.domain.contract.usecase.GetThemeModeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetThemeModeUseCaseImpl @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : GetThemeModeUseCase {

    override fun invoke(): Flow<Boolean> = userPreferencesRepository.userPreferencesFlow
        .map { userPreferences ->
            userPreferences.isDarkMode ?: false
        }
        .flowOn(Dispatchers.IO)
        .catch { e ->
            Log.e("GetThemeModeUseCaseImpl", e.message.toString())
            emit(false)
        }
}