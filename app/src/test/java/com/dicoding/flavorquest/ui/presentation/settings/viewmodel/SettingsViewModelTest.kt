package com.dicoding.flavorquest.ui.presentation.settings.viewmodel

import com.dicoding.core.domain.contract.usecase.GetThemeModeUseCase
import com.dicoding.core.domain.contract.usecase.UpdateThemeModeUseCase
import com.dicoding.flavorquest.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = MainDispatcherRule()

    private var getThemeModeUseCase: GetThemeModeUseCase = mock()
    private var updateThemeModeUseCase: UpdateThemeModeUseCase = mock()
    private lateinit var settingsViewModel: SettingsViewModel

    @Test
    fun init_success() = runTest {
        `when`(getThemeModeUseCase()).thenReturn(flowOf(true))

        settingsViewModel = SettingsViewModel(getThemeModeUseCase, updateThemeModeUseCase)
        advanceUntilIdle()

        val themeMode = settingsViewModel.themeMode.value

        assertNotNull(themeMode)
        assertEquals(true, themeMode)
    }

    @Test
    fun updateThemeMode_callsUseCase() = runTest {
        `when`(getThemeModeUseCase()).thenReturn(flowOf(true))

        settingsViewModel = SettingsViewModel(getThemeModeUseCase, updateThemeModeUseCase)
        val isDarkMode = true

        settingsViewModel.onEvent(SettingEvent.UpdateThemeMode(isDarkMode))
        advanceUntilIdle()

        verify(updateThemeModeUseCase).invoke(isDarkMode)
    }
}