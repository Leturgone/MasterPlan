package com.app.masterplan.data.storage

interface ThemeDataStorage {

    suspend fun getCurrentThemeIsDark(): Boolean

    suspend fun changeTheme(isDarkMode: Boolean)
}