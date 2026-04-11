package com.app.masterplan.presentation.ui.requests.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.masterplan.domain.model.adminRequests.AdminRequest
import com.app.masterplan.domain.model.userManagement.UserRole
import com.app.masterplan.domain.useacse.adminRequests.GetAdminRequestsListUseCase
import com.app.masterplan.domain.useacse.adminRequests.GetCreatedAdminRequestsBySenderListUseCase
import com.app.masterplan.domain.useacse.employee.GetLocalEmpIdUseCase
import com.app.masterplan.domain.useacse.auth.GetUserRoleUseCase
import com.app.masterplan.presentation.ui.common.MasterPlanState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class RequestsListScreenViewModel @Inject constructor(
    private val getAdminRequestsListUseCase: GetAdminRequestsListUseCase,
    private val getCreatedAdminRequestsListUseCase: GetCreatedAdminRequestsBySenderListUseCase,
    private val getLocalEmpIdUseCase: GetLocalEmpIdUseCase,
    private val getUserRoleUseCase: GetUserRoleUseCase
): ViewModel() {

    private val _requestsListFlow = MutableStateFlow<MasterPlanState<List<AdminRequest>>>(
        MasterPlanState.Waiting
    )

    val requestListFlow: StateFlow<MasterPlanState<List<AdminRequest>>> = _requestsListFlow


    private val _showAddButton = MutableStateFlow<Boolean>(false)

    val showAddButton: StateFlow<Boolean> = _showAddButton

    private lateinit var senderId: UUID

    private lateinit var roles: Set<UserRole>

    init {
        viewModelScope.launch {

            roles = getUserRoleUseCase().getOrElse {
                emptySet()
            }

            when {
                UserRole.DIRECTOR in roles -> {
                    getLocalEmpIdUseCase().onSuccess {
                        senderId = it
                        _showAddButton.value = true
                    }.onFailure {
                        _requestsListFlow.value = MasterPlanState.Failure(Exception(it.message))
                    }
                }
            }

        }
    }

    fun loadRequestsList() = viewModelScope.launch {

        _requestsListFlow.value = MasterPlanState.Loading

        if (roles.isEmpty()){
            _requestsListFlow.value = MasterPlanState.Failure(Exception("Не авторизован"))
        }

         when {
            roles.any{it == UserRole.ADMIN} -> {
                val list = getAdminRequestsListUseCase().getOrElse {
                    _requestsListFlow.value = MasterPlanState.Failure(Exception(it.message))
                    return@launch
                }
                _requestsListFlow.value = MasterPlanState.Success(list)
            }

            roles.any{it == UserRole.DIRECTOR} -> {
                val list = getCreatedAdminRequestsListUseCase(senderId).getOrElse {
                    _requestsListFlow.value =  MasterPlanState.Failure(Exception(it.message))
                    return@launch
                }
                _requestsListFlow.value = MasterPlanState.Success(list)
            }
        }

    }
}