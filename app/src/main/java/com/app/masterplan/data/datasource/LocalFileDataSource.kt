package com.app.masterplan.data.datasource

interface LocalFileDataSource {

    suspend fun saveFileToDataStorage(byteArray: ByteArray): String

}