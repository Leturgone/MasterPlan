package com.app.masterplan.presentation.ui.accounts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.masterplan.domain.model.employee.Employee
import com.app.masterplan.domain.model.userManagement.User
import com.app.masterplan.domain.useacse.employee.GetEmployeeByIdUseCase
import com.app.masterplan.domain.useacse.userManagement.DeleteUserUseCase
import com.app.masterplan.domain.useacse.userManagement.GetUserByIdUseCase
import com.app.masterplan.presentation.ui.common.MasterPlanState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountCardViewModel @Inject constructor(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val getEmployeeByIdUseCase: GetEmployeeByIdUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
): ViewModel() {

    private val _isModalVisible = MutableStateFlow(false)
    val isModalVisible: StateFlow<Boolean> = _isModalVisible

    private val _employeeFlow = MutableStateFlow<Employee?>(null)
    val employeeFlow: StateFlow<Employee?> = _employeeFlow

    private val _userDataFlow = MutableStateFlow<MasterPlanState<User>>(MasterPlanState.Waiting)
    val userDataFlow: StateFlow<MasterPlanState<User>> = _userDataFlow

    private val _directorFlow = MutableStateFlow<MasterPlanState<Employee>>(MasterPlanState.Waiting)
    val directorFlow: StateFlow<MasterPlanState<Employee>> = _directorFlow


    fun openAccountTab(employee: Employee) = viewModelScope.launch {

        _employeeFlow.value = employee

        _isModalVisible.value = true

        _userDataFlow.value = MasterPlanState.Loading
        _directorFlow.value = MasterPlanState.Loading

        val directorDataDef = employee.directorId?.let {
            async {getEmployeeByIdUseCase(it) }
        }

        val userDataDef = async { getUserByIdUseCase(employee.userId) }

        val userData = userDataDef.await().getOrElse {
            _userDataFlow.value = MasterPlanState.Failure(Exception(it.message))
            return@launch
        }

        val directorData = directorDataDef?.await()?.getOrElse {
            _userDataFlow.value = MasterPlanState.Failure(Exception(it.message))
            return@launch
        }

        if (directorData == null){
            _directorFlow.value = MasterPlanState.Waiting
        }else{
            _directorFlow.value = MasterPlanState.Success(directorData)
        }

        _userDataFlow.value = MasterPlanState.Success(userData)
    }

    fun deleteUser(afterDelete: () -> Unit) = viewModelScope.launch{
        if (_userDataFlow.value is MasterPlanState.Success){

            val userId = (_userDataFlow.value as MasterPlanState.Success).result.id
            deleteUserUseCase(userId).getOrElse {
                _userDataFlow.value = MasterPlanState.Failure(Exception(it))
            }
            afterDelete()
        }else {
            return@launch
        }
    }
    fun closeRequestTab() {
        _isModalVisible.value = false
        _userDataFlow.value = MasterPlanState.Waiting
        _directorFlow.value = MasterPlanState.Waiting

    }

}