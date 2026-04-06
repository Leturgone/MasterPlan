package com.app.masterplan.domain.dto

import java.io.File

data class AttachedDocument(
    val file: File,
    val fileName: String
)