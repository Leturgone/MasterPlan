package com.app.masterplan.data.datasource

import com.app.masterplan.domain.model.auth.JwtToken

interface TokenDataSource {

    suspend fun getTokenFromDataStorage(): JwtToken

    suspend fun saveTokenToDataStorage(token: JwtToken)
}