package com.app.masterplan.framework

import android.content.Context
import android.net.Uri
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.InputStream

object UriConverter{
    suspend fun uriToByteArray(uri: Uri,context: Context): Result<ByteArray>{
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        if (inputStream == null) {
            Log.e("uriToFile", "Error: Could not open InputStream for URI: $uri")
            return Result.failure(Exception())
        }
        return withContext(Dispatchers.IO){
            try {
                inputStream.use { inputStream ->
                    ByteArrayOutputStream().use { outputStream ->
                        val buffer = ByteArray(4 * 1024)
                        while (true) {
                            val bytes = inputStream.read(buffer)
                            if (bytes < 0) break
                            outputStream.write(buffer, 0, bytes)
                        }
                        Result.success(outputStream.toByteArray())
                    }
                }
            }catch (e: Exception){
                Log.e("uriToByteArray", "Error converting URI to bytes: ${e.message}")
                Result.failure(e)
            }finally {
                inputStream.close()
            }
        }
    }
}