package com.dicoding.favorite.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dicoding.favorite.ui.favorite.FavoriteScreen
import com.dicoding.favorite.ui.favorite.FavoriteViewModel
import com.dicoding.flavorquest.ui.navigation.NavArgument
import com.dicoding.flavorquest.ui.navigation.NavScreen
import com.dicoding.flavorquest.ui.presentation.detail.MealDetailScreen
import com.dicoding.flavorquest.ui.presentation.detail.viewmodel.DetailMealViewModel

@Composable
fun Navigation(
    favoriteViewModel: FavoriteViewModel,
    detailViewModel: DetailMealViewModel,
    onFinish: () -> Unit
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavScreen.Favorite.route,
    ){
        composable(
            route = NavScreen.Favorite.route,
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) }
        ) {
            val state by favoriteViewModel.state.collectAsStateWithLifecycle()
            FavoriteScreen(
                state = state,
                onclick = {
                    navController.navigate(NavScreen.Detail.createRoute(it))
                },
                onError = {
                    onFinish()
                },
                onDelete = {
                    favoriteViewModel.deleteFavorite(it)
                }
            )
        }

        composable(
            NavScreen.Detail.route,
            arguments = listOf(navArgument(NavArgument.ID_MEAL) { type = NavType.StringType }),
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) }
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString(NavArgument.ID_MEAL)

            MealDetailScreen(
                id = id ?: "",
                viewmodel = detailViewModel,
            ) {
                navController.popBackStack()
            }
        }
    }


}