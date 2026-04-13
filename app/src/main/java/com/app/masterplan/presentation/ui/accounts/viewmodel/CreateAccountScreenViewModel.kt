package com.app.masterplan.presentation.ui.accounts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.masterplan.domain.dto.NewEmployeeData
import com.app.masterplan.domain.dto.NewUserData
import com.app.masterplan.domain.model.employee.Employee
import com.app.masterplan.domain.model.userManagement.UserRole
import com.app.masterplan.domain.useacse.userManagement.CreateUserUseCase
import com.app.masterplan.presentation.ui.accounts.components.DirectorData
import com.app.masterplan.presentation.ui.accounts.components.ProfileData
import com.app.masterplan.presentation.ui.accounts.components.RoleData
import com.app.masterplan.presentation.ui.common.MasterPlanState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateAccountScreenViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase,
): ViewModel()  {


    private val _accountData = MutableStateFlow(ProfileData())
    val accountData: StateFlow<ProfileData> = _accountData

    private val _currentTab = MutableStateFlow<Int>(0)
    val currentTab: StateFlow<Int> = _currentTab

    private val _selectedDir = MutableStateFlow<Employee?>(null)

    val selectedDir: StateFlow<Employee?> = _selectedDir


    private val _roles = MutableStateFlow(
        listOf(
            RoleData(UserRole.EMPLOYEE),
            RoleData(UserRole.DIRECTOR)
        )
    )
    val roles: StateFlow<List<RoleData>> = _roles

    private val _savingFlow = MutableStateFlow<MasterPlanState<UUID>>(MasterPlanState.Waiting)

    val savingFlow: StateFlow<MasterPlanState<UUID>> = _savingFlow


    fun saveProfile() = viewModelScope.launch {
        _savingFlow.value = MasterPlanState.Loading
        val userRoles = _roles.value.mapNotNull { role ->
            if (role.isSelected) {
                role.role
            } else {
                null
            }
        }
        val accountData = _accountData.value
        val employeeInfo = NewEmployeeData(
            name = accountData.name,
            surname = accountData.surname,
            patronymic = accountData.patronymic,
            directorId = accountData.directorId
        )
        val newUserData = NewUserData(
            login = accountData.login,
            password = accountData.password,
            roles = userRoles,
            employeeInfo = employeeInfo
        )
         val result = createUserUseCase(newUserData).getOrElse {
            _savingFlow.value =  MasterPlanState.Failure(Exception(it.message))
            return@launch
        }
        _savingFlow.value = MasterPlanState.Success(result)
    }


    fun setCurrentTab(tabId: Int){
        _currentTab.value = tabId
    }


    fun toggleRoleSelection(roleData: RoleData) {
        _roles.value = _roles.value.map { role ->
            if (role == roleData) {
                role.copy(isSelected = !role.isSelected)
            } else {
                role
            }
        }
    }

    fun updateLogin(login: String) {
        _accountData.value = _accountData.value.copy(login = login)
    }

    fun updatePassword(password: String) {
        _accountData.value = _accountData.value.copy(password = password)
    }

    fun updateName(name: String) {
        _accountData.value = _accountData.value.copy(name = name)
    }

    fun updateSurname(surname: String) {
        _accountData.value = _accountData.value.copy(surname = surname)
    }

    fun updatePatronymic(patronymic: String) {
        _accountData.value = _accountData.value.copy(patronymic = patronymic)
    }

    fun updateDirector(director: DirectorData) = viewModelScope.launch {
        _accountData.value = _accountData.value.copy(directorId = director.id)
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