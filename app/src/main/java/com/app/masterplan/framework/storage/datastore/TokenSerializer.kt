package com.app.masterplan.framework.storage.datastore

import com.google.gson.Gson
import java.io.IOException

internal object TokenSerializer {

    private val gson = Gson()


    fun fromJson(json: String): TokenStorageDto {
        try {
            return gson.fromJson(json, TokenStorageDto::class.java)
        } catch (e: Exception) {
            throw IOException("Failed to deserialize token: ${e.message}", e)
        }
    }

    fun toJson(token: TokenStorageDto): String {
        return gson.toJson(token)?: throw IOException("Failed to serialize token")
    }

}