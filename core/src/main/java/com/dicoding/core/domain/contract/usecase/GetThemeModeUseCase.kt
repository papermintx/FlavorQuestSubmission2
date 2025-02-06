package com.dicoding.core.domain.contract.usecase

import kotlinx.coroutines.flow.Flow

interface GetThemeModeUseCase {
    operator fun invoke(): Flow<Boolean>
}