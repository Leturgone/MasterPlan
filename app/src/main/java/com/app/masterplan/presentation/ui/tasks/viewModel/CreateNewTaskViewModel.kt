package com.app.masterplan.presentation.ui.tasks.viewModel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.masterplan.domain.dto.AttachedDocument
import com.app.masterplan.domain.dto.NewTaskData
import com.app.masterplan.domain.useacse.document.AttachFileUseCase
import com.app.masterplan.domain.useacse.plans.AddTaskToPlanUseCase
import com.app.masterplan.presentation.ui.common.MasterPlanState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class CreateNewTaskViewModel @Inject constructor(
    private val addTaskToPlanUseCase: AddTaskToPlanUseCase,
    private val attachFileUseCase: AttachFileUseCase,
): ViewModel() {

    private val _currentCreatingTask = MutableStateFlow(NewTaskData(
        "","", LocalDate.now(), mutableListOf()
    ))

    val currentCreatingTask: StateFlow<NewTaskData> = _currentCreatingTask

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

    fun createNewTaskForPlan(planId: String) = viewModelScope.launch {
        _savingFlow.value = MasterPlanState.Loading
        val planUid = UUID.fromString(planId)
        val taskData = _currentCreatingTask.value
        val document = attachedDocument.value as? MasterPlanState.Success
        val result = addTaskToPlanUseCase(
            planUid, taskData, document?.result
        ).getOrElse {
            _savingFlow.value =  MasterPlanState.Failure(Exception(it.message))
            return@launch
        }
        _savingFlow.value = MasterPlanState.Success(result)
    }

    fun addExecutor(id: UUID) = viewModelScope.launch {
        _currentCreatingTask.value.executorsId.add(id)
    }


    fun updateTitle(title: String) = viewModelScope.launch {
        _currentCreatingTask.value = _currentCreatingTask.value.copy(title = title)
    }

    fun updateDescription(description: String) = viewModelScope.launch {
        _currentCreatingTask.value = _currentCreatingTask.value.copy(description = description)
    }

    fun updateDate(date: LocalDate) = viewModelScope.launch {
        _currentCreatingTask.value = _currentCreatingTask.value.copy(endDate = date)
    }

}