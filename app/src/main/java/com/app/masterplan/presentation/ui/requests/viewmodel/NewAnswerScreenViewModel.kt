package com.app.masterplan.presentation.ui.requests.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.masterplan.domain.useacse.adminRequests.CreateAdminAnswerUseCase
import com.app.masterplan.presentation.ui.common.MasterPlanState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class NewAnswerScreenViewModel @Inject constructor(
    private val createAdminAnswerUseCase: CreateAdminAnswerUseCase
): ViewModel() {

    private val _creatingStatus = MutableStateFlow<MasterPlanState<UUID>>(
        MasterPlanState.Waiting
    )

    val creatingStatus: StateFlow<MasterPlanState<UUID>> = _creatingStatus

    fun createNewAnswer(adminRequestId: String,description: String) = viewModelScope.launch {
        _creatingStatus.value = MasterPlanState.Loading
        val id = UUID.fromString(adminRequestId)
        _creatingStatus.value = createAdminAnswerUseCase(
            "Ответ",description, id
        ).fold(
            onSuccess = { MasterPlanState.Success(it)},
            onFailure = {MasterPlanState.Failure(Exception(it.message))}
        )
    }

}