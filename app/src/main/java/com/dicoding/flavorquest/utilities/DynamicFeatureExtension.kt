package com.dicoding.flavorquest.utilities

import androidx.compose.runtime.Composable
import com.dicoding.core.domain.model.Meal
import com.dicoding.flavorquest.common.StateUi

@Composable
fun DFFavoriteScreen(state: StateUi<List<Meal>>, onclick: (id: String) -> Unit, onError: () -> Unit, onDelete: (meal: Meal) -> Unit) {
    DynamicFeatureUtils.dfFavoriteScreen(state, onclick, onError, onDelete)
}
