package com.app.masterplan.domain.useacse.document

import com.app.masterplan.domain.repository.remote.DocumentRepository
import java.io.File
import java.util.UUID

class DownloadFileUseCase(
    private val documentRepository: DocumentRepository
) {
    suspend operator fun invoke(documentId: UUID): Result<File>{
        return try {
            val document = documentRepository.downloadDocument(documentId)
            Result.success(document)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}