package com.app.masterplan.domain.dto

data class AttachedDocument(
    val fileData: ByteArray,
    val fileName: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AttachedDocument

        if (!fileData.contentEquals(other.fileData)) return false
        if (fileName != other.fileName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = fileData.contentHashCode()
        result = 31 * result + fileName.hashCode()
        return result
    }
}