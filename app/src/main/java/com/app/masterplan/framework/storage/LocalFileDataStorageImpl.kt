package com.app.masterplan.framework.storage

import com.app.masterplan.data.storage.LocalFileDataStorage
import java.io.File

class LocalFileDataStorageImpl(

): LocalFileDataStorage {
    override suspend fun saveFileToDataStorage(byteArray: ByteArray): File {
        TODO("Not yet implemented")
    }


}