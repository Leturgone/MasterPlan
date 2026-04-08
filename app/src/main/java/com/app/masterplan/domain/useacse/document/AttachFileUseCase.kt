package com.app.masterplan.domain.useacse.document

import com.app.masterplan.domain.dto.AttachedDocument
import com.app.masterplan.domain.repository.remote.DocumentRepository

class AttachFileUseCase(
    private val documentRepository: DocumentRepository
) {
    suspend operator fun invoke(filePath: String): Result<AttachedDocument>{
        return try {
            val document = documentRepository.attachLocalFile(filePath)
            Result.success(document)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}