package com.app.masterplan.data.storage

import java.io.File

interface LocalFileDataStorage {

    suspend fun saveFileToDataStorage(byteArray: ByteArray): File

}