package com.app.masterplan.data.storage

import com.app.masterplan.domain.dto.AttachedDocument
import java.io.File

interface LocalFileDataStorage {

    suspend fun saveFileToDataStorage(byteArray: ByteArray): File

    suspend fun getFileByPath(filePath: String): AttachedDocument

}