package com.dicoding.flavorquest.ui.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dicoding.flavorquest.R
import com.dicoding.flavorquest.ui.presentation.home.components.ErrorDialog
import com.dicoding.flavorquest.ui.presentation.home.components.LoadingCard
import com.dicoding.flavorquest.ui.presentation.home.components.MealItem
import com.dicoding.flavorquest.ui.presentation.home.viewmodel.HomeEvent
import com.dicoding.flavorquest.ui.presentation.home.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    goFavorite: () -> Unit,
    goDetail: (String) -> Unit
) {
    val state by viewModel.searchState.collectAsState()
    var searchText by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                goFavorite()
            }) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null
                )

            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    placeholder = { Text(stringResource(R.string.search), style =
                        MaterialTheme.typography.bodyMedium
                    ) },
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            keyboardController?.hide()
                            viewModel.onEvent(HomeEvent.Search(searchText))
                        }
                    )
                )


                if (!state.data.isNullOrEmpty()) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(state.data!!) { meal ->
                            MealItem(meal = meal, modifier = Modifier.clickable {
                                goDetail(meal.id)
                            })
                        }
                    }
                } else if (!state.isLoading) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(R.string.no_data),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }

            }


            // Loading
            LoadingCard(
                modifier = Modifier.align(Alignment.Center),
                isVisible = state.isLoading
            )

            // Error
            ErrorDialog(
                showDialog = state.error.first,
                onDismissRequest = { viewModel.onEvent(HomeEvent.ResetError) },
                errorMessage = state.error.second
            )
        }
    }
}