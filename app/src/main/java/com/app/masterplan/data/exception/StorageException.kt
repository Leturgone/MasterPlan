package com.app.masterplan.data.exception

sealed class StorageException(
    message: String
): Exception(message) {

    class TokenNotFoundException(): StorageException(
        "Token not found"
    )

    class SaveFileException(message: String): StorageException(
        "Error while saving file : $message"
    )

    class LoadingFileException(message: String): StorageException(
        "Error while loading file : $message"
    )

    class SaveTokenException(message: String?): StorageException(
        "Error while getting token : $message"
    )

    class DeleteTokenException(message: String?): StorageException(
        "Error while getting token : $message"
    )
}