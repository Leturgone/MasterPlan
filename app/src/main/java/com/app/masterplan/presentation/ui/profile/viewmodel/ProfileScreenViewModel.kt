package com.app.masterplan.presentation.ui.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.masterplan.domain.model.employee.EmployeeWithMetrics
import com.app.masterplan.domain.useacse.employee.GetLocalEmpIdUseCase
import com.app.masterplan.domain.useacse.employee.GetProfileInformationUseCase
import com.app.masterplan.presentation.ui.common.MasterPlanState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileScreenViewModel(
    private val getProfileInformationUseCase: GetProfileInformationUseCase,
    private val getEmployeeIdUseCase: GetLocalEmpIdUseCase
): ViewModel() {

    private val _profileInformation = MutableStateFlow<MasterPlanState<EmployeeWithMetrics>>(
        MasterPlanState.Waiting
    )

    val profileInformation: StateFlow<MasterPlanState<EmployeeWithMetrics>> = _profileInformation

    fun loadProfile() = viewModelScope.launch {
        val employeeId = getEmployeeIdUseCase().getOrElse {
            _profileInformation.value =  MasterPlanState.Failure(Exception(it.message))
            return@launch
        }

        val profile = getProfileInformationUseCase(employeeId).getOrElse {
            _profileInformation.value =  MasterPlanState.Failure(Exception(it.message))
            return@launch
        }

        _profileInformation.value = MasterPlanState.Success(profile)

    }

}