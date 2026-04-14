package com.app.masterplan.presentation.ui.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.masterplan.domain.model.employee.EmployeeWithMetrics
import com.app.masterplan.domain.model.userManagement.UserRole
import com.app.masterplan.domain.useacse.auth.GetUserRoleUseCase
import com.app.masterplan.domain.useacse.employee.GetLocalEmpIdUseCase
import com.app.masterplan.domain.useacse.employee.GetProfileInformationUseCase
import com.app.masterplan.presentation.ui.common.MasterPlanState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val getProfileInformationUseCase: GetProfileInformationUseCase,
    private val getEmployeeIdUseCase: GetLocalEmpIdUseCase,
    private val getUserRoleUseCase: GetUserRoleUseCase
): ViewModel() {

    private val _profileInformation = MutableStateFlow<MasterPlanState<EmployeeWithMetrics>>(
        MasterPlanState.Waiting
    )

    val profileInformation: StateFlow<MasterPlanState<EmployeeWithMetrics>> = _profileInformation


    private val _showRequestButton = MutableStateFlow<Boolean>(false)

    val showRequestButton: StateFlow<Boolean> = _showRequestButton

    init {
        viewModelScope.launch {
            val roles = getUserRoleUseCase().getOrElse {
                emptySet()
            }
            when {
                UserRole.DIRECTOR in roles -> {
                    _showRequestButton.value = true
                }
            }
        }
    }


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