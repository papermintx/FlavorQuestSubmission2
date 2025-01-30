package com.dicoding.flavorquest.ui.presentation.detail.viewmodel

import android.content.Context
import com.dicoding.core.domain.model.MealDetail

sealed interface DetailEvent {
    data class GetDetail(val id: String): DetailEvent
    data class SetFavorite(val meal: MealDetail, val context: Context): DetailEvent
    data object ResetError: DetailEvent
}