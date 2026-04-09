package com.app.masterplan.framework.storage.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.app.masterplan.data.exception.StorageException
import com.app.masterplan.data.storage.TokenDataStorage
import com.app.masterplan.domain.model.auth.JwtToken
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class TokenDataStorageImpl @Inject constructor(
    private val context: Context
): TokenDataStorage {

    private val TOKEN_KEY = stringPreferencesKey("full_jwt_token")


    override suspend fun getTokenFromDataStorage(): JwtToken {
        return withContext(Dispatchers.IO) {
            Log.i("TokenDataStorage", "Getting token")
            val preferences = context.dataStore.data.first()
            val json = preferences[TOKEN_KEY]
            if (!json.isNullOrEmpty()) {
                val dto = TokenSerializer.fromJson(json)
                Log.i("TokenDataStorage", "Getting token success")
                JwtToken(dto.token, dto.roles, dto.id)
            }else{
                Log.e("TokenDataStorage", "Token not found")
                throw StorageException.TokenNotFoundException()
            }
        }
    }

    override suspend fun saveTokenToDataStorage(token: JwtToken) {
        return withContext(Dispatchers.IO) {
            try {
                Log.i("TokenDataStorage", "Saving token")
                val tokenDto = TokenStorageDto(token.token, token.roles, token.id)
                val json = TokenSerializer.toJson(tokenDto)
                context.dataStore.edit { preferences ->
                    preferences[TOKEN_KEY] = json
                }
                Log.i("TokenDataStorage", "Saving Success")
            }catch (e: Exception){
                Log.e("TokenDataStorage", "Error while saving token: ${e.message}")
                throw StorageException.SaveTokenException(e.message)
            }

        }
    }



    override suspend fun removeTokenFromDataStorage() {
        return withContext(Dispatchers.IO){
            try {
                Log.i("TokenDataStorage", "Deleting token")
                context.dataStore.edit { preferences ->
                    preferences[TOKEN_KEY] = ""
                }
                Log.i("TokenDataStorage", "Delete Success")
            }catch (e: Exception){
                Log.e("TokenDataStorage", "Error while deleting token: ${e.message}")
                throw StorageException.DeleteTokenException(e.message)
            }

        }
    }
}