package com.dicoding.flavorquest.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.dicoding.core.domain.contract.usecase.GetThemeModeUseCase
import com.dicoding.flavorquest.ui.navigation.Navigation
import com.dicoding.flavorquest.ui.theme.FlavorQuestTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var getThemeModeUseCase: GetThemeModeUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val isDarkTheme by getThemeModeUseCase().collectAsState(initial = false)
            FlavorQuestTheme(darkTheme = isDarkTheme) {
                Navigation()
            }
        }
    }
}