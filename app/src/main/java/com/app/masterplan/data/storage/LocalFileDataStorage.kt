package com.app.masterplan.data.storage

interface LocalFileDataStorage {

    suspend fun saveFileToDataStorage(byteArray: ByteArray): String

}