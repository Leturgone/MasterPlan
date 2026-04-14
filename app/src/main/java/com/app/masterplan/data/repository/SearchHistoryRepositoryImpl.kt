package com.app.masterplan.data.repository

import com.app.masterplan.data.storage.SearchHistoryDataStorage
import com.app.masterplan.domain.repository.remote.SearchHistoryRepository
import jakarta.inject.Inject

class SearchHistoryRepositoryImpl @Inject constructor(
    private val searchHistoryDataStorage: SearchHistoryDataStorage
): SearchHistoryRepository {

    override suspend fun saveElementToSearchHistory(element: String) {
        return searchHistoryDataStorage.addElementToSearchHistory(element)
    }

    override suspend fun getSearchHistory(): List<String> {
        return searchHistoryDataStorage.getSearchHistory()
    }

    override suspend fun clearSearchHistory() {
        return searchHistoryDataStorage.clearSearchHistory()
    }
}