package com.dicoding.flavorquest.factory

object DynamicFeatureFactory {

    private const val PACKAGE = "com.dicoding."

    object DFFavorite{
        const val DF_FAVORITE_SCREEN = PACKAGE.plus("favorite.FavoriteScreenKt")
        const val COMPOSE_METHOD_NAME = "FavoriteScreen"
    }

}