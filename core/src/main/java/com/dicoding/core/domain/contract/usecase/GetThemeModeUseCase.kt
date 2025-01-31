package com.dicoding.core.domain.contract.usecase

import kotlinx.coroutines.flow.Flow

interface GetThemeModeUseCase {
    suspend operator fun invoke(): Flow<Boolean>
}