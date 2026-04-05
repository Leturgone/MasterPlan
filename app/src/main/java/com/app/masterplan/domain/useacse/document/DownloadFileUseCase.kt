package com.app.masterplan.domain.useacse.document

import com.app.masterplan.domain.repository.remote.DocumentRepository
import java.util.UUID

class DownloadFileUseCase(
    private val documentRepository: DocumentRepository
) {
    suspend operator fun invoke(documentId: UUID): Result<String>{
        return try {
            val documentPath = documentRepository.downloadDocument(documentId)
            Result.success(documentPath)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}