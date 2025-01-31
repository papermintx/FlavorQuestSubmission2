package com.dicoding.flavorquest.ui.navigation

sealed class NavScreen(val route: String) {
    data object Home: NavScreen("home")
    data object Splash: NavScreen("splash")
    data object Favorite: NavScreen("favorite")
    data object Settings: NavScreen("settings")
    data object About: NavScreen("about")
    data object Detail : NavScreen("detail?id={id}") {
        fun createRoute(id: String): String {
            return "detail?id=$id"
        }
    }

}