package com.app.masterplan.presentation.ui.requests.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.masterplan.domain.useacse.adminRequests.CreateAdminRequestUseCase
import com.app.masterplan.domain.useacse.employee.GetLocalEmpIdUseCase
import com.app.masterplan.presentation.ui.common.MasterPlanState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class NewRequestsScreenViewModel @Inject constructor(
    private val getLocalEmpIdUseCase: GetLocalEmpIdUseCase,
    private val createAdminRequestUseCase: CreateAdminRequestUseCase
): ViewModel() {

    private val _creatingStatus = MutableStateFlow<MasterPlanState<UUID>>(
        MasterPlanState.Waiting
    )

    private lateinit var senderId: UUID

    init {
        viewModelScope.launch {
            senderId = getLocalEmpIdUseCase().getOrElse {
                _creatingStatus.value = MasterPlanState.Failure(
                    Exception(it.message)
                )
                return@launch
            }
        }
    }

    val creatingStatus: StateFlow<MasterPlanState<UUID>> = _creatingStatus

    fun createNewRequest(description: String) = viewModelScope.launch {
        _creatingStatus.value = MasterPlanState.Loading

        _creatingStatus.value = createAdminRequestUseCase(
            "Создать аккаунт",description, senderId
        ).fold(
            onSuccess = { MasterPlanState.Success(it)},
            onFailure = {MasterPlanState.Failure(Exception(it.message))}
        )
    }
}