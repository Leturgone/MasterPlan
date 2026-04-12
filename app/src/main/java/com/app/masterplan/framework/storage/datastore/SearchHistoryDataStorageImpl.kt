package com.app.masterplan.framework.storage.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.app.masterplan.data.exception.StorageException
import com.app.masterplan.data.storage.SearchHistoryDataStorage
import com.google.gson.Gson
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class SearchHistoryDataStorageImpl @Inject constructor(
    private val context: Context
): SearchHistoryDataStorage {

    private val HISTORY_KEY = stringPreferencesKey("search_history")

    override suspend fun addElementToSearchHistory(element: String) {
        return withContext(Dispatchers.IO) {
            try {
                Log.i("SearchHistoryDataStorage", "Saving history")
                val currentHistory = getSearchHistory().toMutableList()

                currentHistory.remove(element)

                currentHistory.add(0, element)

                val limitedHistory = if (currentHistory.size > 10) {
                    currentHistory.subList(0, 10)
                } else {
                    currentHistory
                }

                val jsonHistory = Gson().toJson(limitedHistory)

                context.dataStore.edit { preferences ->
                    preferences[HISTORY_KEY] = jsonHistory
                }

                Log.i("SearchHistoryDataStorage", "Saved history")

            }catch (e: Exception){
                Log.i("SearchHistoryDataStorage", "Saved history")
                throw StorageException.SavingHistoryException(e.message)
            }

        }
    }

    override suspend fun getSearchHistory(): List<String> {
        return withContext(Dispatchers.IO){
            try {
                Log.i("SearchHistoryDataStorage", "Getting history")
                val preferences = context.dataStore.data.first()
                val json = preferences[HISTORY_KEY]
                if (!json.isNullOrEmpty()){
                    Gson().fromJson(json, Array<String>::class.java).toList()
                }else {
                    emptyList()
                }
            }catch (e: Exception){
                Log.i("SearchHistoryDataStorage", "Error while getting history")
                emptyList()
            }

        }
    }

    override suspend fun clearSearchHistory() {
        return withContext(Dispatchers.IO){
            try {
                Log.i("SearchHistoryDataStorage", "Clearing history")
                context.dataStore.edit { preferences ->
                    preferences[HISTORY_KEY] = Gson().toJson(emptyList<String>())
                }
                Log.i("SearchHistoryDataStorage", "Clear Success")
            }catch (e: Exception){
                Log.e("SearchHistoryDataStorage", "Error while clear history: ${e.message}")
                throw StorageException.ClearHistoryException(e.message)
            }

        }
    }
}