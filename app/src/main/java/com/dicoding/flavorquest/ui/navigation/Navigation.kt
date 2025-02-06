package com.dicoding.flavorquest.ui.navigation

import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dicoding.flavorquest.ui.presentation.about.AboutScreen
import com.dicoding.flavorquest.ui.presentation.detail.MealDetailScreen
import com.dicoding.flavorquest.ui.presentation.home.HomeScreen
import com.dicoding.flavorquest.ui.presentation.settings.SettingScreen
import com.dicoding.flavorquest.ui.presentation.splash.SplashScreen

@Composable
fun Navigation(modifier: Modifier = Modifier) {

    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = NavScreen.Splash.route,
        modifier = modifier
    ) {
        composable(
            NavScreen.Home.route,
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) }
        ) {
            HomeScreen(
                goFavorite = {
                    try {
                        context.startActivity(Intent(context, Class.forName("com.dicoding.favorite.FavoriteActivity")))
                    } catch (e: Exception) {
                        Toast.makeText(context, "Module not installed", Toast.LENGTH_SHORT).show()
                    }
                },
                goSetting = { navController.navigate(NavScreen.Settings.route) },
                goAbout = { navController.navigate(NavScreen.About.route) },
                goDetail = { navController.navigate(NavScreen.Detail.createRoute(it)) }
            )
        }
        composable(
            NavScreen.Detail.route,
            arguments = listOf(navArgument(NavArgument.ID_MEAL) { type = NavType.StringType }),
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) }
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString(NavArgument.ID_MEAL)

            MealDetailScreen(id = id ?: "") {
                navController.popBackStack()
            }
        }
        composable(
            route = NavScreen.About.route,
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) }
        ) {
            AboutScreen()
        }
        composable(
            NavScreen.Settings.route,
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) }
        ) {
            SettingScreen()
        }
        composable(
            NavScreen.Splash.route,
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) }
        ) {
            SplashScreen(
                goHome = {
                    navController.navigate(NavScreen.Home.route) {
                        popUpTo(NavScreen.Splash.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
