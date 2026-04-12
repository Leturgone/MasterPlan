package com.app.masterplan.domain.useacse.theme

import com.app.masterplan.domain.repository.remote.ThemeRepository

class GetCurrentThemeIsDarkUseCase(
    private val themeRepository: ThemeRepository
) {
    suspend operator fun invoke(): Result<Boolean>{
        return try {
            val result = themeRepository.getCurrentThemeIsDark()
            Result.success(result)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}