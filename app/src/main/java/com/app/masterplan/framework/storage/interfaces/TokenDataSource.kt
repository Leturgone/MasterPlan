package com.app.masterplan.framework.storage.interfaces

import com.app.masterplan.domain.model.auth.JwtToken

interface TokenDataSource {

    suspend fun getTokenFromDataStorage(): JwtToken

    suspend fun saveTokenToDataStorage(token: JwtToken)
}