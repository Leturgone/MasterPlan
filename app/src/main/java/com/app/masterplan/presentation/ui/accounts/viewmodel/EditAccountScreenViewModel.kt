package com.app.masterplan.presentation.ui.accounts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.masterplan.domain.model.employee.Employee
import com.app.masterplan.domain.model.userManagement.User
import com.app.masterplan.domain.useacse.employee.GetEmployeeByIdUseCase
import com.app.masterplan.domain.useacse.employee.UpdateEmployeeUseCase
import com.app.masterplan.domain.useacse.userManagement.GetUserByIdUseCase
import com.app.masterplan.domain.useacse.userManagement.ResetPasswordUseCase
import com.app.masterplan.presentation.ui.accounts.components.DirectorData
import com.app.masterplan.presentation.ui.accounts.components.ProfileData
import com.app.masterplan.presentation.ui.common.MasterPlanState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class EditAccountScreenViewModel @Inject constructor(
    private val getEmployeeByIdUseCase: GetEmployeeByIdUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase,
    private val updateEmployeeUseCase: UpdateEmployeeUseCase
): ViewModel()  {


    private val _profileData = MutableStateFlow(ProfileData())
    val profileData: StateFlow<ProfileData> = _profileData

    private val _selectedDir = MutableStateFlow<Employee?>(null)

    val selectedDir: StateFlow<Employee?> = _selectedDir

    private val _editPassword = MutableStateFlow<Boolean>(false)

    private val _loadEditEmployeeFlow = MutableStateFlow<MasterPlanState<User>>(MasterPlanState.Waiting)
    val loadEditEmployeeFlow: StateFlow<MasterPlanState<User>> = _loadEditEmployeeFlow


    private val _savingFlow = MutableStateFlow<MasterPlanState<UUID>>(MasterPlanState.Waiting)

    val savingFlow: StateFlow<MasterPlanState<UUID>> = _savingFlow

    fun loadEditedProfile(id: String) = viewModelScope.launch {
        val uid = UUID.fromString(id)
        val employee = getEmployeeByIdUseCase(uid).getOrElse {
            _loadEditEmployeeFlow.value = MasterPlanState.Failure(Exception(it.message))
            return@launch
        }
        val directorDataDef = employee.directorId?.let {
            async {getEmployeeByIdUseCase(it) }
        }

        val userDataDef = async { getUserByIdUseCase(employee.userId) }

        val userData = userDataDef.await().getOrElse {
            _loadEditEmployeeFlow.value = MasterPlanState.Failure(Exception(it.message))
            return@launch
        }

        val directorData = directorDataDef?.await()?.getOrElse {
            _loadEditEmployeeFlow.value = MasterPlanState.Failure(Exception(it.message))
            return@launch
        }

        _profileData.value = ProfileData(
            login = userData.login,
            password = userData.password,
            roles = userData.roles.toList(),
            name = employee.name,
            surname = employee.surname,
            patronymic = employee.patronymic,
            directorId = employee.directorId,
            userId = userData.id,
            employeeId = uid
        )
        _selectedDir.value = directorData

        _loadEditEmployeeFlow.value = MasterPlanState.Success(userData)
    }



    fun updateProfile() = viewModelScope.launch {
        _savingFlow.value = MasterPlanState.Loading

        val accountData = _profileData.value

        if (accountData.userId!=null && accountData.employeeId!=null){
            if (_editPassword.value){
                resetPasswordUseCase(accountData.userId,accountData.password).getOrElse {
                    _savingFlow.value =  MasterPlanState.Failure(Exception(it.message))
                    return@launch
                }
            }

            val newEmployee = Employee(
                id = accountData.employeeId,
                name = accountData.name,
                surname = accountData.surname,
                patronymic = accountData.patronymic,
                directorId = accountData.directorId,
                userId = accountData.userId
            )
            val result = updateEmployeeUseCase(accountData.employeeId, newEmployee).getOrElse {
                _savingFlow.value =  MasterPlanState.Failure(Exception(it.message))
                return@launch
            }

            _savingFlow.value = MasterPlanState.Success(result)
        }
    }




    fun updatePassword(password: String) {
        _profileData.value = _profileData.value.copy(password = password)
        _editPassword.value = true
    }

    fun updateName(name: String) {
        _profileData.value = _profileData.value.copy(name = name)
    }

    fun updateSurname(surname: String) {
        _profileData.value = _profileData.value.copy(surname = surname)
    }

    fun updatePatronymic(patronymic: String) {
        _profileData.value = _profileData.value.copy(patronymic = patronymic)
    }

    fun updateDirector(director: DirectorData) = viewModelScope.launch {
        _profileData.value = _profileData.value.copy(directorId = director.id)
        _selectedDir.value = Employee(
            id = director.id,
            name = director.name,
            surname = director.surname,
            patronymic = director.patronymic,
            directorId = director.directorId,
            userId = director.userId
        )
    }


}