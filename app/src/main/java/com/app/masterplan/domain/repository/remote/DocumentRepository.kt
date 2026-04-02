package com.app.masterplan.domain.repository.remote

import java.util.UUID

interface DocumentRepository {

    suspend fun downloadDocument(documentId: UUID): ByteArray
}