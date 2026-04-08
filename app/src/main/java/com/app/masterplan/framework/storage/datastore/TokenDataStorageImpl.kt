package com.app.masterplan.framework.storage.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.app.masterplan.data.storage.TokenDataStorage
import com.app.masterplan.domain.model.auth.JwtToken
import com.google.gson.Gson
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.io.IOException

class TokenDataStorageImpl @Inject constructor(
    private val context: Context
): TokenDataStorage {


    override suspend fun getTokenFromDataStorage(): JwtToken {
        return withContext(Dispatchers.IO) {
            val preferences = context.dataStore.data.first()
            val token = preferences.token
            val roles = preferences.roles
            val id  = preferences.id
            JwtToken(token, roles, id)
        }
    }

    override suspend fun saveTokenToDataStorage(token: JwtToken) {
        return withContext(Dispatchers.IO) {
            val tokenDto = TokenStorageDto(token.token, token.roles, token.id)
            context.dataStore.updateData { tokenDto }
        }
    }

}