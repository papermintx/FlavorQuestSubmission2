package com.dicoding.flavorquest.common

data class StateUi<T>(
    val isLoading: Boolean = false,
    val data: T? = null,
    val error: Pair<Boolean, String> = Pair(false, "")
)
