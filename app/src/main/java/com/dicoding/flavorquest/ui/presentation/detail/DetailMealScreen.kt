package com.dicoding.flavorquest.ui.presentation.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dicoding.flavorquest.R
import com.dicoding.flavorquest.ui.presentation.detail.components.IngredientRow
import com.dicoding.flavorquest.ui.presentation.detail.components.YoutubePlay
import com.dicoding.flavorquest.ui.presentation.detail.viewmodel.DetailEvent
import com.dicoding.flavorquest.ui.presentation.detail.viewmodel.DetailMealViewModel
import com.dicoding.flavorquest.ui.presentation.home.components.ErrorDialog
import com.dicoding.flavorquest.ui.presentation.home.components.LoadingDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealDetailScreen(
    id: String,
    viewmodel: DetailMealViewModel = hiltViewModel(),
    goBack: () -> Unit
) {

    val state by viewmodel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewmodel.onEvent(DetailEvent.GetDetail(id))

    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        state.data?.strMeal ?: "Detail Screen",
                        style = MaterialTheme.typography.titleMedium,
                        color =  MaterialTheme.colorScheme.onPrimary
                    ) },
                        colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
            )
        },

    ){ paddingValues ->
        Box(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
        ) {

            if(state.data != null){
                val data = state.data!!
                Column(modifier = Modifier
                    .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    if (data.strYoutube.isNotEmpty()) {
                        val videoIdFromLinkYoutube = data.strYoutube.split("v=")[1]
                        YoutubePlay(videoId = videoIdFromLinkYoutube)
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(6.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .verticalScroll(rememberScrollState()),
                        ) {

                            Text(
                                text = data.strCategory + " - " + data.strArea,
                                style = MaterialTheme.typography.titleSmall,
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = stringResource(id = R.string.instruction),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight =  FontWeight.SemiBold
                            )

                            Text(
                                text = data.strInstructions,
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Justify
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = stringResource(id = R.string.ingredients_with_measure),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(3.dp))

                            data.ingredientsWithMeasures.forEach { (ingredient, measure) ->
                                IngredientRow(
                                    ingredient = ingredient,
                                    measure = measure,
                                    modifier = Modifier.padding(1.dp)
                                )
                            }
                        }

                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    val context = LocalContext.current

                    Button(onClick = {
                        viewmodel.onEvent(DetailEvent.SetFavorite(data, context))
                    }) {
                        Text(text = stringResource(id = R.string.add_favorite))
                    }
                    Spacer(modifier = Modifier.height(8.dp))


                }
            }



            LoadingDialog(isVisible = state.isLoading)

            ErrorDialog(showDialog = state.error.first, errorMessage = state.error.second) {
                viewmodel.onEvent(DetailEvent.ResetError)
                goBack()
            }
        }

    }
}
