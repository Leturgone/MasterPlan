package com.app.masterplan.framework.storage

import com.app.masterplan.data.storage.TokenDataStorage
import com.app.masterplan.domain.model.auth.JwtToken

class TokenDataStorageImpl(

): TokenDataStorage{
    override suspend fun getTokenFromDataStorage(): JwtToken {
        TODO("Not yet implemented")
    }

    override suspend fun saveTokenToDataStorage(token: JwtToken) {
        TODO("Not yet implemented")
    }

}