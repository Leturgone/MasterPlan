package com.app.masterplan.domain.useacse.searchHistory

import com.app.masterplan.domain.repository.remote.SearchHistoryRepository

class ClearSearchHistoryUseCase(
    private val searchHistoryRepository: SearchHistoryRepository
) {
    suspend operator fun invoke(): Result<Unit>{
        return try {
            val result = searchHistoryRepository.clearSearchHistory()
            Result.success(result)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}