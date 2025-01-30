package com.dicoding.flavorquest.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF2F855A),       // Hijau gelap (melambangkan keberlanjutan dan stabilitas keuangan)
    onPrimary = Color.White,           // Warna teks pada warna utama (Putih)
    secondary = Color(0xFF38B2AC),     // Hijau kebiruan (melambangkan investasi dan keuangan)
    onSecondary = Color.White,         // Warna teks pada warna sekunder
    tertiary = Color(0xFF2C5282),      // Biru tua (melambangkan kestabilan dan kepercayaan)
    onTertiary = Color.White,          // Warna teks pada warna pelengkap
    background = Color(0xFF1A202C),    // Latar belakang gelap yang netral
    onBackground = Color(0xFFE2E8F0),  // Warna teks pada latar belakang
    surface = Color(0xFF2D3748),       // Warna permukaan gelap yang netral
    onSurface = Color(0xFFE2E8F0),     // Warna teks pada permukaan
    primaryContainer = Color(0xFF2F855A) // Warna kontainer utama (hijau gelap)
)


private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF2F855A),       // Hijau gelap untuk mode terang (melambangkan keberlanjutan keuangan)
    onPrimary = Color.White,           // Warna teks pada warna utama (Putih)
    secondary = Color(0xFF38B2AC),     // Hijau kebiruan (investasi dan stabilitas)
    onSecondary = Color.White,         // Warna teks pada warna sekunder
    tertiary = Color(0xFFDD6B20),      // Oranye emas (melambangkan kekayaan dan nilai)
    onTertiary = Color.White,          // Warna teks pada warna pelengkap
    background = Color(0xFFF7FAFC),    // Warna latar terang yang bersih dan profesional
    onBackground = Color(0xFF2D3748),  // Warna teks pada latar belakang
    surface = Color(0xFFFFFFFF),       // Warna permukaan terang
    onSurface = Color(0xFF2D3748),     // Warna teks pada permukaan terang
    primaryContainer = Color(0xFFE2E8F0) // Warna kontainer utama (hijau muda atau netral)
)



@Composable
fun FlavorQuestTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppFonts,
        content = content
    )
}