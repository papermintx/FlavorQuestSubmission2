# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute
#-keep class androidx.** { *; }

# Keep Compose Material Icons
-keep class androidx.compose.material.icons.** { *; }
-keep class androidx.compose.material.icons.filled.** { *; }
-keep class androidx.compose.material.icons.outlined.** { *; }
-keep class androidx.compose.material.icons.rounded.** { *; }
-keep class androidx.compose.material.icons.sharp.** { *; }

-dontwarn com.dicoding.core.common.State$Empty
-dontwarn com.dicoding.core.common.State$Error
-dontwarn com.dicoding.core.common.State$Loading
-dontwarn com.dicoding.core.common.State$Success
-dontwarn com.dicoding.core.common.State
-dontwarn com.dicoding.core.data.mapper.MappersKt
-dontwarn com.dicoding.core.data.remote.ApiService
-dontwarn com.dicoding.core.di.CoreModule_ProvideApiServiceFactory
-dontwarn com.dicoding.core.di.CoreModule_ProvideDeleteFavoriteUseCaseFactory
-dontwarn com.dicoding.core.di.CoreModule_ProvideGetAllFavoriteUseCaseFactory
-dontwarn com.dicoding.core.di.CoreModule_ProvideGetAllMealInitialUseCaseFactory
-dontwarn com.dicoding.core.di.CoreModule_ProvideGetThemeModeUseCaseFactory
-dontwarn com.dicoding.core.di.CoreModule_ProvideInsertMealFavoriteUseCaseFactory
-dontwarn com.dicoding.core.di.CoreModule_ProvideLoggingInterceptorFactory
-dontwarn com.dicoding.core.di.CoreModule_ProvideMealDetailUseCaseFactory
-dontwarn com.dicoding.core.di.CoreModule_ProvideMoshiFactory
-dontwarn com.dicoding.core.di.CoreModule_ProvideOkHttpClientFactory
-dontwarn com.dicoding.core.di.CoreModule_ProvideRecipeRepositoryFactory
-dontwarn com.dicoding.core.di.CoreModule_ProvideRemoteDataSourceFactory
-dontwarn com.dicoding.core.di.CoreModule_ProvideRetrofitFactory
-dontwarn com.dicoding.core.di.CoreModule_ProvideSearchMealUseCaseFactory
-dontwarn com.dicoding.core.di.CoreModule_ProvideUpdateThemeModeUseCaseFactory
-dontwarn com.dicoding.core.di.DataStoreModule_ProvideDataStoreFactory
-dontwarn com.dicoding.core.di.DataStoreModule_ProvideUserPreferencesRepositoryFactory
-dontwarn com.dicoding.core.di.LocalModule_ProvideLocalDataSourceFactory
-dontwarn com.dicoding.core.di.LocalModule_ProvideMealDaoFactory
-dontwarn com.dicoding.core.di.LocalModule_ProvideMealDetailDaoFactory
-dontwarn com.dicoding.core.di.LocalModule_ProvideMealFavoriteDaoFactory
-dontwarn com.dicoding.core.di.LocalModule_ProvideStoryDatabaseFactory
-dontwarn com.dicoding.core.domain.contract.repository.RecipeRepository
-dontwarn com.dicoding.core.domain.contract.repository.UserPreferencesRepository
-dontwarn com.dicoding.core.domain.contract.source.LocalDataSource
-dontwarn com.dicoding.core.domain.contract.source.RemoteDataSource
-dontwarn com.dicoding.core.domain.contract.usecase.DeleteMealFavoriteUseCase
-dontwarn com.dicoding.core.domain.contract.usecase.GetAllFavoriteUseCase
-dontwarn com.dicoding.core.domain.contract.usecase.GetAllMealInitialUseCase
-dontwarn com.dicoding.core.domain.contract.usecase.GetMealDetailUseCase
-dontwarn com.dicoding.core.domain.contract.usecase.GetThemeModeUseCase
-dontwarn com.dicoding.core.domain.contract.usecase.InsertFavoriteMealUseCase
-dontwarn com.dicoding.core.domain.contract.usecase.SearchMealUseCase
-dontwarn com.dicoding.core.domain.contract.usecase.UpdateThemeModeUseCase
-dontwarn com.dicoding.core.domain.model.Meal
-dontwarn com.dicoding.core.domain.model.MealDetail