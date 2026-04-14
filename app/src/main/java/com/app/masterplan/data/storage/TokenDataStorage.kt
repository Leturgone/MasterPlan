package com.app.masterplan.data.storage

import com.app.masterplan.domain.model.auth.JwtToken

interface TokenDataStorage {

    suspend fun getTokenFromDataStorage(): JwtToken

    suspend fun saveTokenToDataStorage(token: JwtToken)

    suspend fun removeTokenFromDataStorage()
}