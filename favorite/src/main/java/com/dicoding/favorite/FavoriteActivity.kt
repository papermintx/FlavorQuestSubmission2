package com.dicoding.favorite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dicoding.favorite.di.DaggerFavoriteComponent
import com.dicoding.favorite.di.ViewModelFactory
import com.dicoding.favorite.ui.favorite.FavoriteViewModel
import com.dicoding.favorite.ui.navigation.Navigation
import com.dicoding.flavorquest.di.FavoriteModuleDependencies
import com.dicoding.flavorquest.ui.presentation.detail.viewmodel.DetailMealViewModel
import com.dicoding.flavorquest.ui.theme.FlavorQuestTheme
import com.dicoding.flavorquest.ui.theme.ThemeViewModel
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteActivity : ComponentActivity() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: FavoriteViewModel by viewModels { factory }
    private val detailViewModel: DetailMealViewModel by viewModels { factory }
    private val themeViewModel: ThemeViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFavoriteComponent.builder()
            .context(this)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    applicationContext,
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDarkTheme by themeViewModel.isDarkMode.collectAsStateWithLifecycle()
            FlavorQuestTheme(darkTheme = isDarkTheme) {
                Navigation(
                    favoriteViewModel = viewModel,
                    detailViewModel = detailViewModel
                ){
                    finish()
                }
            }
        }
    }

}

