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

# Mencegah class penting dihapus oleh R8
-keep class com.dicoding.core.data.mapper.MappersKt { *; }
-keep class com.dicoding.core.data.remote.ApiService { *; }

# Keep semua factory Hilt/Dagger agar dependency injection tetap berjalan
-keep class com.dicoding.core.di.** { *; }

# Pastikan repository dan use case tetap ada
-keep class com.dicoding.core.domain.contract.repository.RecipeRepository { *; }
-keep class com.dicoding.core.domain.contract.source.** { *; }
-keep class com.dicoding.core.domain.contract.usecase.** { *; }

# Pastikan semua model domain tetap tersedia
-keep class com.dicoding.core.domain.model.** { *; }

# Pastikan semua sealed class dan enum di `State` tetap ada
-keep class com.dicoding.core.common.State$* { *; }

-keep class com.dicoding.core.domain.usecase.** { *; }
