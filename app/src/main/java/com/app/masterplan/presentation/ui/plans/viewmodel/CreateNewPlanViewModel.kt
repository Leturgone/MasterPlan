package com.app.masterplan.presentation.ui.plans.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.masterplan.domain.dto.AttachedDocument
import com.app.masterplan.domain.dto.NewPlanData
import com.app.masterplan.domain.useacse.document.AttachFileUseCase
import com.app.masterplan.domain.useacse.employee.GetLocalEmpIdUseCase
import com.app.masterplan.domain.useacse.plans.CreatePlanUseCase
import com.app.masterplan.presentation.ui.common.MasterPlanState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateNewPlanViewModel @Inject constructor(
    private val getLocalEmpIdUseCase: GetLocalEmpIdUseCase,
    private val createNewPlanUseCase: CreatePlanUseCase,
    private val attachFileUseCase: AttachFileUseCase,
): ViewModel() {

    private lateinit var directorId: UUID

    init {
        viewModelScope.launch {
            getLocalEmpIdUseCase().onSuccess {
                directorId = it
            }.onFailure {
                return@launch
            }
        }
    }

    private val _currentCreatingPlan = MutableStateFlow(NewPlanData(
        "","", LocalDate.now(), LocalDate.now(), directorId
    ))

    val currentCreatingPlan: StateFlow<NewPlanData> = _currentCreatingPlan

    private val _attachedDocument = MutableStateFlow<MasterPlanState<AttachedDocument>>(
        MasterPlanState.Waiting
    )

    val attachedDocument: StateFlow<MasterPlanState<AttachedDocument>> = _attachedDocument

    private val _savingFlow = MutableStateFlow<MasterPlanState<UUID>>(MasterPlanState.Waiting)

    val savingFlow: StateFlow<MasterPlanState<UUID>> = _savingFlow

    fun attachDocument(uri: Uri?) = viewModelScope.launch {
        uri?: return@launch
        _attachedDocument.value = MasterPlanState.Loading
        val result = attachFileUseCase(uri.toString()).getOrElse {
            _attachedDocument.value =  MasterPlanState.Failure(Exception(it.message))
            return@launch
        }
        _attachedDocument.value = MasterPlanState.Success(result)
    }

    fun createNewPlan() = viewModelScope.launch {
        _savingFlow.value = MasterPlanState.Loading
        val planData = _currentCreatingPlan.value
        val document = attachedDocument.value as? MasterPlanState.Success

        val result = createNewPlanUseCase(planData, document?.result).getOrElse {
            _savingFlow.value =  MasterPlanState.Failure(Exception(it.message))
            return@launch
        }
        _savingFlow.value = MasterPlanState.Success(result)
    }


    fun updateTitle(title: String) = viewModelScope.launch {
        _currentCreatingPlan.value = _currentCreatingPlan.value.copy(title = title)
    }

    fun updateDescription(description: String) = viewModelScope.launch {
        _currentCreatingPlan.value = _currentCreatingPlan.value.copy(description = description)
    }

    fun updateDate(date: LocalDate) = viewModelScope.launch {
        _currentCreatingPlan.value = _currentCreatingPlan.value.copy(endDate = date)
    }

}