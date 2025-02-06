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
#-renamesourcefileattribute SourceFile

-dontwarn java.lang.invoke.StringConcatFactory
# Pastikan semua model DTO tidak dihapus atau diubah namanya
-keep class com.dicoding.core.data.remote.response.** { *; }

# Pastikan Moshi tetap bisa melakukan parsing JSON
-keep class com.squareup.moshi.** { *; }
-keep class **JsonAdapter { *; }

# Hindari perubahan nama pada field JSON
-keepattributes RuntimeVisibleAnnotations
-keepattributes *Annotation*

# Mencegah class penting dihapus oleh R8
-keep class com.dicoding.core.data.mapper.MappersKt { *; }
-keep class com.dicoding.core.data.remote.ApiService { *; }

-keep class com.dicoding.core.di.** { *; }

-keep class com.dicoding.core.domain.contract.repository.RecipeRepository { *; }
-keep class com.dicoding.core.domain.contract.source.** { *; }
-keep class com.dicoding.core.domain.contract.usecase.** { *; }

-keep class com.dicoding.core.domain.model.** { *; }

-keep class com.dicoding.core.common.State$* { *; }

-keep class com.dicoding.core.domain.usecase.DeleteMealFavoriteUseCaseImpl { *; }
-keep class com.dicoding.core.domain.usecase.GetAllFavoriteUseCaseImpl { *; }
-keep class com.dicoding.core.domain.usecase.InsertFavoriteMealUseCaseImpl { *; }
-keep class com.dicoding.core.domain.usecase.GetAllInitialMealImplUseCase { *; }
-keep class com.dicoding.core.domain.usecase.GetMealDetailUseCaseImpl { *; }
-keep class com.dicoding.core.domain.usecase.GetThemeModeUseCaseImpl { *; }
-keep class com.dicoding.core.domain.usecase.SearchMealUseCaseImpl { *; }
-keep class com.dicoding.core.domain.usecase.UpdateThemeModeUseCaseImpl { *; }