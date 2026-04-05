package com.app.masterplan.framework.storage.interfaces

interface TokenDataSource {

    suspend fun getTokenFromDataStorage(): String

    suspend fun saveTokenToDataStorage()
}