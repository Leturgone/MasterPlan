package com.app.masterplan.framework.storage.filesStorage

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.OpenableColumns
import android.util.Log
import androidx.core.net.toUri
import com.app.masterplan.data.storage.LocalFileDataStorage
import com.app.masterplan.domain.dto.AttachedDocument
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.InputStream

class LocalFileDataStorageImpl(
    private val context: Context
): LocalFileDataStorage {

    override suspend fun saveFileToDataStorage(byteArray: ByteArray,ext: String): File {
        return withContext(Dispatchers.IO){
            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val destinationFile = File(downloadsDir,
                "download_${System.currentTimeMillis().toInt()}.$ext"
            )
            destinationFile.outputStream().write(byteArray)
            destinationFile
        }

    }

    override suspend fun getFileByPath(filePath: String): AttachedDocument {
        val uri = filePath.toUri()
        val fileName = getFileNameFromUri(uri)?:"file"
        val file = uriToFile(uri,context,fileName)
        return AttachedDocument(
            file = file,
            fileName = fileName
        )

    }

    suspend fun uriToFile(uri: Uri,context: Context, fileName: String): File{
        return withContext(Dispatchers.IO){
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            if (inputStream == null) {
                Log.e("uriToFile", "Error: Could not open InputStream for URI: $uri")
                throw Exception(" Could not open InputStream for URI: $uri")
            }
            try {
                val outputFile = File(context.cacheDir, fileName)

                outputFile.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream, bufferSize = 4 * 1024)
                }

                outputFile
            }catch (e: Exception){
                Log.e("uriToByteArray", "Error converting URI to bytes: ${e.message}")
                throw Exception("Error converting URI to bytes: ${e.message}")
            }finally {
                inputStream.close()
            }
        }
    }

    private fun getFileNameFromUri(uri: Uri): String? {
        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            cursor.moveToFirst()
            return cursor.getString(nameIndex)
        }
        return null
    }


}