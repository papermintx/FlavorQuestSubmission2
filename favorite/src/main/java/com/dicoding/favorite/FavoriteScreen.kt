package com.dicoding.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dicoding.core.domain.model.Meal
import com.dicoding.favorite.components.EmptyState
import com.dicoding.flavorquest.common.StateUi
import com.dicoding.flavorquest.ui.presentation.home.components.ErrorDialog
import com.dicoding.flavorquest.ui.presentation.home.components.LoadingDialog
import com.dicoding.flavorquest.ui.presentation.home.components.MealItem
import com.dicoding.flavorquest.R

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("unused")
@Composable
fun FavoriteScreen(
    state: StateUi<List<Meal>>,
    onclick: (id: String) -> Unit,
    onError: () -> Unit,
    onDelete: (meal: Meal) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.favorite_title),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .padding(horizontal = 4.dp)
                    .fillMaxSize()
            ) {
                state.data?.let { data ->
                    items(data, key = { it.id }) { meal ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {

                            MealItem(meal = meal, modifier = Modifier.clickable {
                                onclick(meal.id)
                            })

                            Card(
                                modifier = Modifier.align(Alignment.TopEnd),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.onSurface
                                )
                            ) {
                                IconButton(
                                    onClick = {
                                        onDelete(meal)
                                    }
                                ) {
                                    Icon(Icons.Default.Delete, tint = Color.Red, contentDescription = stringResource(id =R.string.delete_button))
                                }
                            }
                        }
                    }
                }
            }

            if (state.data.isNullOrEmpty()) {
                EmptyState()
            }

            LoadingDialog(isVisible = state.isLoading)

            ErrorDialog(showDialog = state.error.first, errorMessage = state.error.second) {
                onError()
            }
        }
    }
}
