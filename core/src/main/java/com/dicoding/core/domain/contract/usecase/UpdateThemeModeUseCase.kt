package com.dicoding.core.domain.contract.usecase

interface UpdateThemeModeUseCase {
    suspend operator fun invoke(isDarkMode: Boolean)
}