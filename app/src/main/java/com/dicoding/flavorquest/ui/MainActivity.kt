package com.dicoding.flavorquest.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import com.dicoding.core.domain.contract.usecase.GetThemeModeUseCase
import com.dicoding.flavorquest.ui.navigation.Navigation
import com.dicoding.flavorquest.ui.theme.FlavorQuestTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var getThemeModeUseCase: GetThemeModeUseCase

    private var isDarkTheme by mutableStateOf(false)

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


    private suspend fun getThemeMode() {
        getThemeModeUseCase().collect { isDark ->
            isDarkTheme = isDark
        }
    }

}