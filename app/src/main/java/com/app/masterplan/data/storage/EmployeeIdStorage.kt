package com.app.masterplan.data.storage

import java.util.UUID

interface EmployeeIdStorage {

    suspend fun getLocalEmployeeId(): UUID

    suspend fun saveLocalEmployeeId(id: UUID)

    suspend fun deleteLocalEmployeeId()
}