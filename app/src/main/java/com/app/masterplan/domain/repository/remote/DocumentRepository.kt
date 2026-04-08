package com.app.masterplan.domain.repository.remote

import com.app.masterplan.domain.dto.AttachedDocument
import java.io.File
import java.util.UUID

interface DocumentRepository {

    suspend fun downloadDocument(documentId: UUID): File

    suspend fun attachLocalFile(filePath: String): AttachedDocument
}