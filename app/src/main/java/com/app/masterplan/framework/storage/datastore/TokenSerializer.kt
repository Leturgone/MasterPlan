package com.app.masterplan.framework.storage.datastore

import android.annotation.SuppressLint
import androidx.datastore.core.Serializer
import com.google.gson.Gson
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID

internal object TokenSerializer: Serializer<TokenStorageDto> {

    private val gson = Gson()

    override val defaultValue: TokenStorageDto
        get() = TokenStorageDto("",emptySet(), UUID.randomUUID())

    @SuppressLint("CheckResult")
    override suspend fun readFrom(input: InputStream): TokenStorageDto {
        try {
            val json = input.readBytes().decodeToString()
            return gson.fromJson(json, TokenStorageDto::class.java)
        } catch (e: Exception) {
            throw IOException("Failed to deserialize token: ${e.message}", e)
        }
    }

    override suspend fun writeTo(
        t: TokenStorageDto,
        output: OutputStream
    ) {
        output.write(
            gson.toJson(t).encodeToByteArray()
        )
    }

}