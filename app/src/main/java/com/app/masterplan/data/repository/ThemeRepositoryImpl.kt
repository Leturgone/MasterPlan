package com.app.masterplan.data.repository

import com.app.masterplan.data.storage.ThemeDataStorage
import com.app.masterplan.domain.repository.remote.ThemeRepository

class ThemeRepositoryImpl(
    private val themeDataStorage: ThemeDataStorage
): ThemeRepository {
    override suspend fun getCurrentThemeIsDark(): Boolean {
        return themeDataStorage.getCurrentThemeIsDark()
    }

    override suspend fun changeTheme(isDarkMode: Boolean) {
        themeDataStorage.changeTheme(isDarkMode)
    }
}