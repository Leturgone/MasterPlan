package com.app.masterplan.domain.useacse.searchHistory

import com.app.masterplan.domain.repository.remote.SearchHistoryRepository

class GetSearchHistoryUseCase(
    private val searchHistoryRepository: SearchHistoryRepository
) {
    suspend operator fun invoke(): Result<List<String>>{
        return try {
            val result = searchHistoryRepository.getSearchHistory()
            Result.success(result)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}
