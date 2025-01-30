package com.dicoding.flavorquest.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dicoding.flavorquest.ui.presentation.detail.MealDetailScreen
import com.dicoding.flavorquest.ui.presentation.favorite.FavoriteViewModel
import com.dicoding.flavorquest.ui.presentation.home.HomeScreen
import com.dicoding.flavorquest.ui.presentation.splash.SplashScreen
import com.dicoding.flavorquest.utilities.DFFavoriteScreen

@Composable
fun Navigation(modifier: Modifier = Modifier) {

    val navController = rememberNavController()



    NavHost(
        navController = navController,
        startDestination = NavScreen.Splash.route,
        modifier = modifier
    ) {
        composable(NavScreen.Home.route) {
            HomeScreen(
                goFavorite = {
                    navController.navigate(NavScreen.Favorite.route){
                        popUpTo(navController.graph.startDestinationId)
                    }
                },
                goDetail = {
                    navController.navigate(NavScreen.Detail.createRoute(it))
                }
            )
        }
        composable(
            NavScreen.Detail.route,
            arguments = listOf(navArgument(NavArgument.ID_MEAL) { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString(NavArgument.ID_MEAL)

            MealDetailScreen(id = id ?: "") {
                navController.popBackStack()
            }

        }
        composable(route = NavScreen.Favorite.route) {
            val favoriteViewModel = hiltViewModel<FavoriteViewModel>()
            val state by favoriteViewModel.state.collectAsState()
            DFFavoriteScreen(
                state = state,
                onclick = {
                    navController.navigate(NavScreen.Detail.createRoute(it))
                },
                onError = {},
                onDelete = {
                    favoriteViewModel.deleteFavorite(it)
                }
            )
        }
        composable(
            NavScreen.Splash.route
        ) {
            SplashScreen(
                goHome = {
                    navController.navigate(NavScreen.Home.route){
                        popUpTo(NavScreen.Splash.route){
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}