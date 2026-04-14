package com.app.masterplan.presentation.ui.employees.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.masterplan.domain.model.employee.EmployeeWithMetrics
import com.app.masterplan.domain.model.plans.Task
import com.app.masterplan.domain.useacse.employee.GetProfileInformationUseCase
import com.app.masterplan.domain.useacse.plans.GetAssignedTasksUseCase
import com.app.masterplan.presentation.ui.common.MasterPlanState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class EmployeeCardViewModel @Inject constructor(
    private val getProfileInformationUseCase: GetProfileInformationUseCase,
    private val getAssignedTasksUseCase: GetAssignedTasksUseCase
): ViewModel() {

    private val _isModalVisible = MutableStateFlow(false)
    val isModalVisible: StateFlow<Boolean> = _isModalVisible

    private val _employeeDataFlow = MutableStateFlow< MasterPlanState<EmployeeWithMetrics>>(
        MasterPlanState.Waiting
    )
    val employeeDataFlow: StateFlow<MasterPlanState<EmployeeWithMetrics>> = _employeeDataFlow

    private val _currentTaskFlow = MutableStateFlow<MasterPlanState<Task>>(
        MasterPlanState.Waiting
    )

    val currentTaskFlow: StateFlow<MasterPlanState<Task>> = _currentTaskFlow

    fun openEmployeeTab(employeeId: UUID) = viewModelScope.launch {
        _isModalVisible.value = true
        _employeeDataFlow.value = MasterPlanState.Loading
        _currentTaskFlow.value = MasterPlanState.Loading
        val empDataDef = async { getProfileInformationUseCase(employeeId) }
        val taskDataDef = async { getAssignedTasksUseCase(employeeId) }

        val empData = empDataDef.await().getOrElse {
            _employeeDataFlow.value = MasterPlanState.Failure(Exception(it.message))
            return@launch
        }

        val taskData = taskDataDef.await().getOrElse {
            _employeeDataFlow.value = MasterPlanState.Failure(Exception(it.message))
            return@launch
        }
        val firstTask = taskData.getOrNull(0)

        if (firstTask == null){
            _currentTaskFlow.value = MasterPlanState.Waiting
        }else{
            _currentTaskFlow.value = MasterPlanState.Success(firstTask)
        }

        _employeeDataFlow.value = MasterPlanState.Success(empData)

    }

    fun closeRequestTab() = viewModelScope.launch {
        _isModalVisible.value = false
        _employeeDataFlow.value = MasterPlanState.Waiting
        _currentTaskFlow.value = MasterPlanState.Waiting

    }

}