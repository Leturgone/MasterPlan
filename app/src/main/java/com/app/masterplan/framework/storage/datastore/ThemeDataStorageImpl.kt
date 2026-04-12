package com.app.masterplan.framework.storage.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.app.masterplan.data.storage.ThemeDataStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class ThemeDataStorageImpl(
    private val context: Context
): ThemeDataStorage {

    private val APP_THEME_KEY = booleanPreferencesKey("app_theme")

    override suspend fun getCurrentThemeIsDark(): Boolean {
        return withContext(Dispatchers.IO){
            val preferences = context.dataStore.data.first()
            preferences[APP_THEME_KEY] ?: false
        }
    }

    override suspend fun changeTheme(isDarkMode: Boolean) {
        return withContext(Dispatchers.IO){
            context.dataStore.edit { preferences ->
                preferences[APP_THEME_KEY] = isDarkMode
            }
        }
    }
}