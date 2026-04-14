package com.app.masterplan.data.storage

interface SearchHistoryDataStorage {

    suspend fun addElementToSearchHistory(element: String)

    suspend fun getSearchHistory(): List<String>

    suspend fun clearSearchHistory()
}