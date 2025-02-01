package com.dicoding.flavorquest.ui.presentation.home.viewmodel

sealed interface HomeEvent {
    data class Search(val query: String): HomeEvent
    data object ResetError: HomeEvent
    data object FetchInitialMeals: HomeEvent // for testing
}