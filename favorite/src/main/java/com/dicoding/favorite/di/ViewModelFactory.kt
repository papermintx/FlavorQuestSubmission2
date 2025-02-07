package com.dicoding.favorite.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.core.domain.contract.usecase.DeleteMealFavoriteUseCase
import com.dicoding.core.domain.contract.usecase.GetAllFavoriteUseCase
import com.dicoding.core.domain.contract.usecase.GetMealDetailUseCase
import com.dicoding.core.domain.contract.usecase.GetThemeModeUseCase
import com.dicoding.core.domain.contract.usecase.InsertFavoriteMealUseCase
import com.dicoding.favorite.ui.favorite.FavoriteViewModel
import com.dicoding.flavorquest.ui.presentation.detail.viewmodel.DetailMealViewModel
import com.dicoding.flavorquest.ui.theme.ThemeViewModel
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(
    private val getAllFavoriteUseCase: GetAllFavoriteUseCase,
    private val deleteMealFavoriteUseCase: DeleteMealFavoriteUseCase,
    private val getDetailMealUseCase: GetMealDetailUseCase,
    private val insertMealFavoriteUseCase: InsertFavoriteMealUseCase,
    private val getThemeModeUseCase: GetThemeModeUseCase
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(getAllFavoriteUseCase, deleteMealFavoriteUseCase) as T
            }
            modelClass.isAssignableFrom(DetailMealViewModel::class.java) -> {
                DetailMealViewModel(getDetailMealUseCase, insertMealFavoriteUseCase) as T
            }
            modelClass.isAssignableFrom(ThemeViewModel::class.java) -> {
                ThemeViewModel(getThemeModeUseCase) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
