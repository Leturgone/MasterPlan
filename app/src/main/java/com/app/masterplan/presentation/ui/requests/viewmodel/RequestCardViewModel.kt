package com.app.masterplan.presentation.ui.requests.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.masterplan.domain.model.adminRequests.AdminAnswer
import com.app.masterplan.domain.model.adminRequests.AdminRequest
import com.app.masterplan.domain.model.adminRequests.AdminRequestStatus
import com.app.masterplan.domain.model.userManagement.UserRole
import com.app.masterplan.domain.useacse.adminRequests.ChangeAdminRequestStatusUseCase
import com.app.masterplan.domain.useacse.adminRequests.GetAdminAnswerForRequestUseCase
import com.app.masterplan.domain.useacse.auth.GetUserRoleUseCase
import com.app.masterplan.domain.useacse.employee.GetEmployeeByIdUseCase
import com.app.masterplan.presentation.ui.common.MasterPlanState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestCardViewModel @Inject constructor(
    private val getUserRoleUseCase: GetUserRoleUseCase,
    private val updateAdminRequestStatusUseCase: ChangeAdminRequestStatusUseCase,
    private val getAdminAnswerForRequestUseCase: GetAdminAnswerForRequestUseCase,
    private val getEmployeeByIdUseCase: GetEmployeeByIdUseCase
): ViewModel() {
    private val _isModalVisible = MutableStateFlow(false)
    val isModalVisible: StateFlow<Boolean> = _isModalVisible

    private val _selectedRequest = MutableStateFlow<AdminRequest?>(null)
    val selectedRequest: StateFlow<AdminRequest?> = _selectedRequest

    private val _selectedRequestStatus = MutableStateFlow<AdminRequestStatus>(AdminRequestStatus.INVALID)
    val selectedRequestStatus: StateFlow<AdminRequestStatus> = _selectedRequestStatus

    private val _senderInfo = MutableStateFlow<MasterPlanState<String>>(
        MasterPlanState.Waiting)

    val senderInfo: StateFlow<MasterPlanState<String>> = _senderInfo


    private val _showGetInWorkButton = MutableStateFlow<Boolean>(false)

    val showGetInWorkButton: StateFlow<Boolean> = _showGetInWorkButton

    private val _showCreateAnswerButton = MutableStateFlow<Boolean>(false)

    val showCreateAnswerButton: StateFlow<Boolean> =_showCreateAnswerButton

    private val _showAnswerInfo = MutableStateFlow<Boolean>(false)

    val showAnswerInfo: StateFlow<Boolean> = _showAnswerInfo

    private val _answerInfo = MutableStateFlow<MasterPlanState<AdminAnswer>>(
        MasterPlanState.Waiting)

    val answerInfo: StateFlow<MasterPlanState<AdminAnswer>> = _answerInfo

    private lateinit var roles: Set<UserRole>

    init {
        viewModelScope.launch {

            roles = getUserRoleUseCase().getOrElse {
                emptySet()
            }

        }
    }


    fun openRequestTab(request: AdminRequest) = viewModelScope.launch {
        _selectedRequest.value = request
        _selectedRequestStatus.value = request.status

        when{
            roles.any{it == UserRole.ADMIN} && request.status == AdminRequestStatus.NOT_STARTED -> {
                _showGetInWorkButton.value = true
            }

            roles.any{it == UserRole.ADMIN} && request.status == AdminRequestStatus.IN_PROGRESS -> {
                _showCreateAnswerButton.value = true
            }

            request.status == AdminRequestStatus.COMPLETED -> {

                _showAnswerInfo.value = true

                _answerInfo.value = MasterPlanState.Loading

                val answer =  async { getAdminAnswerForRequestUseCase(request.id) }

                _answerInfo.value = answer.await().fold(
                    onSuccess = {
                        MasterPlanState.Success(it)
                    },
                    onFailure = {
                        MasterPlanState.Failure(Exception(it.message))
                    }
                )
            }
        }

        _isModalVisible.value = true


        _senderInfo.value = MasterPlanState.Loading


        val empName = async{
            getEmployeeByIdUseCase(request.senderId)
        }


        _senderInfo.value = empName.await().fold(
            onSuccess = {
                val name = it.name
                val surname = it.surname
                val patronymic = it.patronymic ?: ""

                val fullName = "$surname ${name[0]}.${patronymic[0]}"

                MasterPlanState.Success(fullName)
            },
            onFailure = {
                MasterPlanState.Success("Неизвестно")
            }
        )



    }

    fun getInWork() = viewModelScope.launch {
        val requestId = selectedRequest.value?.id ?: return@launch
        updateAdminRequestStatusUseCase(
            requestId, AdminRequestStatus.IN_PROGRESS
        ).onSuccess {
            _selectedRequestStatus.value = AdminRequestStatus.IN_PROGRESS
        }

    }


    fun closeRequestTab() = viewModelScope.launch {
        _isModalVisible.value = false
        //resetState()
    }

    fun resetState() {
        _selectedRequest.value = null
        _selectedRequestStatus.value = AdminRequestStatus.INVALID
        _senderInfo.value = MasterPlanState.Waiting
        _answerInfo.value = MasterPlanState.Waiting
        _showGetInWorkButton.value = false
        _showCreateAnswerButton.value = false
        _showAnswerInfo.value = false
    }
}