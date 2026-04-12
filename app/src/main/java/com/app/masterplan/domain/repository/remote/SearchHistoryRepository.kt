package com.app.masterplan.domain.repository.remote

interface SearchHistoryRepository {

    suspend fun saveElementToSearchHistory(element: String)

    suspend fun getSearchHistory(): List<String>

    suspend fun clearSearchHistory()
}