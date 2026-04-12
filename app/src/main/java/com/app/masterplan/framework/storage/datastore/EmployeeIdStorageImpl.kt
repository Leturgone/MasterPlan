package com.app.masterplan.framework.storage.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.app.masterplan.data.exception.StorageException
import com.app.masterplan.data.storage.EmployeeIdStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.util.UUID

class EmployeeIdStorageImpl(
    private val context: Context
): EmployeeIdStorage {

    private val EMPLOYEE_KEY = stringPreferencesKey("local_emp_id")

    override suspend fun getLocalEmployeeId(): UUID {
        return withContext(Dispatchers.IO){
            try {
                Log.i("EmployeeIdStorage", "Getting employeeId")
                val preferences = context.dataStore.data.first()
                val id = UUID.fromString(preferences[EMPLOYEE_KEY])
                Log.i("EmployeeIdStorage", "Getting employeeId Success")
                id
            }catch (e: Exception){
                Log.e("EmployeeIdStorage", "employeeId not found: ${e.message}")
                throw StorageException.EmployeeIdNotFoundException()
            }

        }
    }

    override suspend fun saveLocalEmployeeId(id: UUID) {
        return withContext(Dispatchers.IO){
            try {
                Log.i("EmployeeIdStorage", "Saving employeeId")
                val uuid = id.toString()
                context.dataStore.edit { preferences ->
                    preferences[EMPLOYEE_KEY] = uuid
                }
                Log.i("EmployeeIdStorage", "Saving Success")
            }catch (e: Exception){
                Log.e("EmployeeIdStorage", "Error while saving employeeId: ${e.message}")
                throw StorageException.SaveEmployeeIdException(e.message)
            }
        }
    }

    override suspend fun deleteLocalEmployeeId() {
        return withContext(Dispatchers.IO){
            try {
                Log.i("EmployeeIdStorage", "Deleting employeeId")
                context.dataStore.edit { preferences ->
                    preferences[EMPLOYEE_KEY] = ""
                }
                Log.i("EmployeeIdStorage", "Delete Success")
            }catch (e: Exception){
                Log.e("EmployeeIdStorage", "Error deleting employeeId: ${e.message}")
                throw StorageException.DeleteEmployeeIdException(e.message)
            }
        }
    }
}