package com.dicoding.core.domain.usecase

import com.dicoding.core.domain.contract.repository.UserPreferencesRepository
import com.dicoding.core.domain.contract.usecase.UpdateThemeModeUseCase
import javax.inject.Inject

class UpdateThemeModeUseCaseImpl @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
): UpdateThemeModeUseCase {

    override suspend fun invoke(isDarkMode: Boolean) {
        userPreferencesRepository.updateDarkMode(isDarkMode)
    }

}