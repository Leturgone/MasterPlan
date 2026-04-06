package com.app.masterplan.data.mapper

import com.app.masterplan.domain.dto.AttachedDocument
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

object MultipartCreator {

    fun toMultipartBodyPart(document: AttachedDocument): MultipartBody.Part{
        val requestFile = document.file.asRequestBody("application/pdf".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(
            name = "file",
            filename = document.fileName,
            body = requestFile
        )
    }
}