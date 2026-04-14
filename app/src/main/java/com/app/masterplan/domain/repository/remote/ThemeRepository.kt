package com.app.masterplan.domain.repository.remote

interface ThemeRepository {

    suspend fun getCurrentThemeIsDark(): Boolean

    suspend fun changeTheme(isDarkMode: Boolean)
}