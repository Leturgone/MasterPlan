package com.app.masterplan.domain.repository.remote

import java.io.File
import java.util.UUID

interface DocumentRepository {

    suspend fun downloadDocument(documentId: UUID): File
}