package com.dicoding.flavorquest.utilities

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import com.dicoding.core.domain.model.Meal
import com.dicoding.flavorquest.common.StateUi
import com.dicoding.flavorquest.factory.DynamicFeatureFactory
import com.dicoding.flavorquest.ui.presentation.df_not_found.DFNotFoundScreen


object DynamicFeatureUtils {

    const val TAG = "DynamicFeatureUtils"

    @Composable
    fun dfFavoriteScreen(state: StateUi<List<Meal>>, onclick: (id: String) -> Unit, onError: () -> Unit, onDelete:(meal: Meal) -> Unit): Boolean {
        return loadDF(
            state, onclick, onError, onDelete,
            className = DynamicFeatureFactory.DFFavorite.DF_FAVORITE_SCREEN,
            methodName = DynamicFeatureFactory.DFFavorite.COMPOSE_METHOD_NAME
        )
    }

    @Composable
    fun ShowDFNotFoundScreen() {
        DFNotFoundScreen()
    }

    @Composable
    private fun loadDF(
        state: StateUi<List<Meal>>, onclick: (id: String) -> Unit, onError: () -> Unit, onDelete: (meal: Meal) -> Unit,
        className: String,
        methodName: String,
        objectInstance: Any = Any()
    ): Boolean {
        val dfClass = loadClassByReflection(className)
        Log.d(TAG, "loadDF: $dfClass")
        if (dfClass != null) {
            val composer = currentComposer
            val method = findMethodByReflection(
                dfClass,
                methodName
            )

            Log.d(TAG, "loadDF: $method")
            if (method != null) {
                val isMethodInvoked =
                    invokeMethod(method, objectInstance, state, onclick, onError, onDelete, composer, 0)
                Log.d(TAG, "loadDF: $isMethodInvoked")
                if (!isMethodInvoked) {
                    Log.d(TAG, "loadDF: Method not invoked")
                    ShowDFNotFoundScreen()
                    return false
                }
                Log.d(TAG, "loadDF: Method invoked2")
                return true
            } else {
                Log.d(TAG, "loadDF: Method not found")
                ShowDFNotFoundScreen()
                return false
            }
        } else {
            Log.d(TAG, "loadDF: Class not found")
            ShowDFNotFoundScreen()
            return false
        }
    }




}