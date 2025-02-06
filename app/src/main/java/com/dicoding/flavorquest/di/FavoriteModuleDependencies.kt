package com.dicoding.flavorquest.di

import com.dicoding.core.domain.contract.usecase.DeleteMealFavoriteUseCase
import com.dicoding.core.domain.contract.usecase.GetAllFavoriteUseCase
import com.dicoding.core.domain.contract.usecase.GetMealDetailUseCase
import com.dicoding.core.domain.contract.usecase.GetThemeModeUseCase
import com.dicoding.core.domain.contract.usecase.InsertFavoriteMealUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {

    fun provideGetAllFavoriteUseCase(): GetAllFavoriteUseCase
    fun provideDeleteMealFavoriteUseCase(): DeleteMealFavoriteUseCase
    fun provideInsertFavoriteMealUseCase(): InsertFavoriteMealUseCase
    fun provideGetMealDetailUseCase(): GetMealDetailUseCase
    fun provideGetThemeModeUseCase(): GetThemeModeUseCase

}