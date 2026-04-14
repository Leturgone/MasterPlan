package com.app.masterplan.data.api.filesApi

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import java.util.UUID

interface FilesApi {

    @GET("emp/file/{fileId}")
    suspend fun downloadFile(
        @Header("Authorization") token: String,
        @Path(value = "fileId") fileId: UUID
    ): Response<ResponseBody>


}