package com.dicoding.flavorquest.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import com.dicoding.flavorquest.ui.navigation.Navigation
import com.dicoding.flavorquest.ui.presentation.home.HomeScreen
import com.dicoding.flavorquest.ui.theme.FlavorQuestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlavorQuestTheme {
               Surface {
                   Navigation()
               }
            }
        }
    }
}