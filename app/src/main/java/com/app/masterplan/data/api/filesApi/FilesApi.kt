package com.app.masterplan.data.api.filesApi

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import java.util.UUID

interface FilesApi {

    @GET("/files/emp/file/{fileId}")
    suspend fun downloadFile(
        @Path("fileId") fileId: UUID,
        @Header("Authorization") token: String,
    ): Response<ResponseBody>


}