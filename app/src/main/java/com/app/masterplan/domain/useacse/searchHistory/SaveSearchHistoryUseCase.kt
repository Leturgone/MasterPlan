package com.app.masterplan.domain.useacse.searchHistory

import com.app.masterplan.domain.repository.remote.SearchHistoryRepository

class SaveSearchHistoryUseCase(
    private val searchHistoryRepository: SearchHistoryRepository
) {
    suspend operator fun invoke(element: String): Result<Unit> {
        return try {
            val result = searchHistoryRepository.saveElementToSearchHistory(element)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}