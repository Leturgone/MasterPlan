package com.app.masterplan.presentation.ui.tasks.viewModel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.masterplan.domain.dto.AttachedDocument
import com.app.masterplan.domain.model.plans.Task
import com.app.masterplan.domain.useacse.document.AttachFileUseCase
import com.app.masterplan.domain.useacse.plans.GetTaskInfUseCase
import com.app.masterplan.domain.useacse.plans.UpdateTaskUseCase
import com.app.masterplan.presentation.ui.common.MasterPlanState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class UpdateTaskViewModel  @Inject constructor(
    private val getTaskByIdUseCase: GetTaskInfUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val attachFileUseCase: AttachFileUseCase
) : ViewModel() {

    private val _attachedDocument = MutableStateFlow<MasterPlanState<AttachedDocument>>(MasterPlanState.Waiting)
    val attachedDocument: StateFlow<MasterPlanState<AttachedDocument>> = _attachedDocument

    private val _savingFlow = MutableStateFlow<MasterPlanState<UUID>>(MasterPlanState.Waiting)
    val savingFlow: StateFlow<MasterPlanState<UUID>> = _savingFlow

    private val _loadingTask = MutableStateFlow<MasterPlanState<Task>>(MasterPlanState.Waiting)
    val loadingTask: StateFlow<MasterPlanState<Task>> = _loadingTask

    private val _updatedTask = MutableStateFlow<Task?>(null)

    val updatedTask: StateFlow<Task?> = _updatedTask

    fun loadTask(taskId: String) = viewModelScope.launch {
        _loadingTask.value = MasterPlanState.Loading
        val taskUid = UUID.fromString(taskId)
        val result = getTaskByIdUseCase(taskUid).getOrElse {
            _loadingTask.value = MasterPlanState.Failure(Exception(it.message))
            return@launch
        }
        _updatedTask.value = result
        _loadingTask.value = MasterPlanState.Success(result)
    }

    fun attachDocument(uri: Uri?) = viewModelScope.launch {
        uri ?: return@launch
        _attachedDocument.value = MasterPlanState.Loading
        val result = attachFileUseCase(uri.toString()).getOrElse {
            _attachedDocument.value = MasterPlanState.Failure(Exception(it.message))
            return@launch
        }
        _attachedDocument.value = MasterPlanState.Success(result)
    }

    fun updateTask() = viewModelScope.launch {
        _savingFlow.value = MasterPlanState.Loading

        val updatedTask = _updatedTask.value ?: return@launch

        val document = attachedDocument.value as? MasterPlanState.Success

        val result = updateTaskUseCase(
            updatedTask.id,
            updatedTask,
            document?.result
        ).getOrElse {
            _savingFlow.value = MasterPlanState.Failure(Exception(it.message))
            return@launch
        }
        _savingFlow.value = MasterPlanState.Success(result)
    }

    fun updateTitle(title: String) = viewModelScope.launch {
        _updatedTask.value ?: return@launch
        _updatedTask.value = _updatedTask.value!!.copy(title = title)
    }

    fun updateDescription(description: String) = viewModelScope.launch {
        _updatedTask.value ?: return@launch
        _updatedTask.value = _updatedTask.value!!.copy(description = description)
    }

    fun updateDate(date: LocalDate) = viewModelScope.launch {
        _updatedTask.value ?: return@launch
        _updatedTask.value = _updatedTask.value!!.copy(endDate = date)
    }

    fun addExecutor(id: UUID) = viewModelScope.launch {
        val executors: MutableList<UUID> = _updatedTask.value?.executorsIds?.toMutableList() ?: return@launch
        executors.add(id)
        _updatedTask.value ?: return@launch
        _updatedTask.value = _updatedTask.value!!.copy(executorsIds = executors)
    }
}