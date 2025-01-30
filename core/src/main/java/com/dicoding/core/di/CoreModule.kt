package com.dicoding.core.di

import com.dicoding.core.BuildConfig
import com.dicoding.core.data.remote.ApiService
import com.dicoding.core.data.remote.RemoteDataSourceImpl
import com.dicoding.core.data.repository.RecipeRepositoryImpl
import com.dicoding.core.domain.contract.repository.RecipeRepository
import com.dicoding.core.domain.contract.source.LocalDataSource
import com.dicoding.core.domain.contract.source.RemoteDataSource
import com.dicoding.core.domain.contract.usecase.DeleteMealFavoriteUseCase
import com.dicoding.core.domain.contract.usecase.GetAllFavoriteUseCase
import com.dicoding.core.domain.contract.usecase.GetAllMealInitial
import com.dicoding.core.domain.contract.usecase.GetMealDetailUseCase
import com.dicoding.core.domain.contract.usecase.InsertFavoriteMealUseCase
import com.dicoding.core.domain.contract.usecase.InsertInitialMeal
import com.dicoding.core.domain.contract.usecase.SearchMealUseCase
import com.dicoding.core.domain.usecase.DeleteMealFavoriteUseCaseImpl
import com.dicoding.core.domain.usecase.GetAllFavoriteUseCaseImpl
import com.dicoding.core.domain.usecase.GetAllInitialMealImpl
import com.dicoding.core.domain.usecase.GetMealDetailUseCaseImpl
import com.dicoding.core.domain.usecase.InsertFavoriteMealImpl
import com.dicoding.core.domain.usecase.InsertInitialMealImpl
import com.dicoding.core.domain.usecase.SearchMealUseCaseImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object CoreModule {

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(25, TimeUnit.SECONDS)
            .readTimeout(25, TimeUnit.SECONDS)
            .writeTimeout(25, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun provideRemoteDataSource(apiService: ApiService): RemoteDataSource {
        return RemoteDataSourceImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideRecipeRepository(remoteDataSourceImpl: RemoteDataSource, localDataSource: LocalDataSource): RecipeRepository {
        return RecipeRepositoryImpl(remoteDataSourceImpl, localDataSource)
    }

    @Provides
    @Singleton
    fun provideSearchMealUseCase(recipeRepository: RecipeRepository): SearchMealUseCase {
        return SearchMealUseCaseImpl(recipeRepository)
    }

    @Provides
    @Singleton
    fun provideMealDetailUseCase(recipeRepository: RecipeRepository): GetMealDetailUseCase {
        return GetMealDetailUseCaseImpl(recipeRepository)
    }

    @Provides
    @Singleton
    fun provideInsertInitialMealUseCase(recipeRepository: RecipeRepository): InsertInitialMeal {
        return InsertInitialMealImpl(recipeRepository)
    }

    @Provides
    @Singleton
    fun provideGetAllMealInitialUseCase(recipeRepository: RecipeRepository): GetAllMealInitial {
        return GetAllInitialMealImpl(recipeRepository)
    }

    @Provides
    @Singleton
    fun provideInsertMealFavoriteUseCase(recipeRepository: RecipeRepository): InsertFavoriteMealUseCase {
        return InsertFavoriteMealImpl(recipeRepository)
    }

    @Provides
    @Singleton
    fun provideGetAllFavoriteUseCase(recipeRepository: RecipeRepository): GetAllFavoriteUseCase {
        return GetAllFavoriteUseCaseImpl(recipeRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteFavoriteUseCase(recipeRepository: RecipeRepository): DeleteMealFavoriteUseCase {
        return DeleteMealFavoriteUseCaseImpl(recipeRepository)
    }

}