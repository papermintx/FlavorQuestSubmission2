package com.dicoding.flavorquest.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dicoding.flavorquest.ui.navigation.Navigation
import com.dicoding.flavorquest.ui.theme.FlavorQuestTheme
import com.dicoding.flavorquest.ui.theme.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private  val viewModel: ThemeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val isDarkTheme by viewModel.isDarkMode.collectAsStateWithLifecycle(initialValue = false)
            FlavorQuestTheme(darkTheme = isDarkTheme) {
                Navigation()
            }
        }
    }
}