package com.app.masterplan.data.exception

sealed class StorageException(
    message: String
): Exception(message) {

    class TokenNotFoundException(): StorageException(
        "Token not found"
    )

    class EmployeeIdNotFoundException(): StorageException(
        "EmployeeId not found"
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


    class SaveEmployeeIdException(message: String?): StorageException(
        "Error while getting employeeId : $message"
    )

    class DeleteEmployeeIdException(message: String?): StorageException(
        "Error while getting employeeId : $message"
    )

    class SavingHistoryException(message: String?): StorageException(
        "Error while saving history : $message"
    )

    class ClearHistoryException(message: String?): StorageException(
        "Error while clear history : $message"
    )
}