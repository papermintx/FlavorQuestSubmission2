package com.dicoding.flavorquest.di

import com.dicoding.core.domain.contract.usecase.DeleteMealFavoriteUseCase
import com.dicoding.core.domain.contract.usecase.GetAllFavoriteUseCase
import com.dicoding.core.domain.contract.usecase.GetAllMealInitialUseCase
import com.dicoding.core.domain.contract.usecase.GetMealDetailUseCase
import com.dicoding.core.domain.contract.usecase.GetThemeModeUseCase
import com.dicoding.core.domain.contract.usecase.InsertFavoriteMealUseCase
import com.dicoding.core.domain.contract.usecase.InsertInitialMealUseCase
import com.dicoding.core.domain.contract.usecase.SearchMealUseCase
import com.dicoding.core.domain.contract.usecase.UpdateThemeModeUseCase
import com.dicoding.core.domain.usecase.DeleteMealFavoriteUseCaseImpl
import com.dicoding.core.domain.usecase.GetAllFavoriteUseCaseImpl
import com.dicoding.core.domain.usecase.GetAllInitialMealImplUseCase
import com.dicoding.core.domain.usecase.GetMealDetailUseCaseImpl
import com.dicoding.core.domain.usecase.GetThemeModeUseCaseImpl
import com.dicoding.core.domain.usecase.InsertFavoriteMealUseCaseImpl
import com.dicoding.core.domain.usecase.InsertInitialMealUseCaseImpl
import com.dicoding.core.domain.usecase.SearchMealUseCaseImpl
import com.dicoding.core.domain.usecase.UpdateThemeModeUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindGetAllFavoriteUseCase(impl: GetAllFavoriteUseCaseImpl): GetAllFavoriteUseCase

    @Binds
    @Singleton
    abstract fun bindDeleteMealFavoriteUseCase(impl: DeleteMealFavoriteUseCaseImpl): DeleteMealFavoriteUseCase

    @Binds
    @Singleton
    abstract  fun bindInsertFavoriteMealUseCase(impl: InsertFavoriteMealUseCaseImpl): InsertFavoriteMealUseCase

    @Binds
    @Singleton
    abstract fun bindGetDetailMealUseCase(impl: GetMealDetailUseCaseImpl): GetMealDetailUseCase

    @Binds
    @Singleton
    abstract fun bindSearchMealUseCase(impl: SearchMealUseCaseImpl): SearchMealUseCase

    @Binds
    @Singleton
    abstract fun bindInsertInitialMealUseCase(impl: InsertInitialMealUseCaseImpl): InsertInitialMealUseCase

    @Binds
    @Singleton
    abstract fun bindGetAllMealInitialUseCase(impl: GetAllInitialMealImplUseCase): GetAllMealInitialUseCase

    @Binds
    @Singleton
    abstract  fun  bindGetThemeModeUseCase(impl: GetThemeModeUseCaseImpl): GetThemeModeUseCase

    @Binds
    @Singleton
    abstract  fun bindUpdateThemeModeUseCase(impl: UpdateThemeModeUseCaseImpl): UpdateThemeModeUseCase

}

