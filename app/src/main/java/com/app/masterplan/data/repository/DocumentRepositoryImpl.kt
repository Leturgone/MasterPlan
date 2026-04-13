package com.app.masterplan.data.repository

import com.app.masterplan.data.api.exception.ApiErrorResponse
import com.app.masterplan.data.api.filesApi.FilesApi
import com.app.masterplan.data.exception.ApiException
import com.app.masterplan.data.mapper.ApiErrorResponseHandler
import com.app.masterplan.domain.repository.remote.DocumentRepository
import com.app.masterplan.data.storage.LocalFileDataStorage
import com.app.masterplan.data.storage.TokenDataStorage
import com.app.masterplan.domain.dto.AttachedDocument
import java.io.File
import java.util.UUID
import javax.inject.Inject

class DocumentRepositoryImpl  @Inject constructor(
    private val filesApi: FilesApi,
    private val localFileDataSource: LocalFileDataStorage,
    private val tokenStorage: TokenDataStorage
): DocumentRepository {


    override suspend fun downloadDocument(documentId: UUID): File {
        val token = tokenStorage.getTokenFromDataStorage()
        val response = filesApi.downloadFile(token.token,documentId)
        val byteArray = ApiErrorResponseHandler.handleResponse(response,::errorMapper).bytes()
        val savedPath = localFileDataSource.saveFileToDataStorage(byteArray,"pdf")
        return savedPath
    }

    override suspend fun attachLocalFile(filePath: String): AttachedDocument {
        val file = localFileDataSource.getFileByPath(filePath)
        return file
    }

    private fun errorMapper(errorResp: ApiErrorResponse): ApiException.FilesApiException{
        return ApiException.FilesApiException(
            status = errorResp.status,
            apiMessage = errorResp.message
        )
    }

}