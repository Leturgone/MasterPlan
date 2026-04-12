package com.app.masterplan.domain.useacse.theme

import com.app.masterplan.domain.repository.remote.ThemeRepository

class ChangeThemeUseCase(
    private val themeRepository: ThemeRepository
) {
    suspend operator fun invoke(isDarkMode: Boolean): Result<Unit>{
        return try {
            val result = themeRepository.changeTheme(isDarkMode)
            Result.success(result)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}