package com.app.masterplan.framework.storage.interfaces

interface LocalFileDataSource {

    suspend fun saveFileToDataStorage(byteArray: ByteArray): String

}