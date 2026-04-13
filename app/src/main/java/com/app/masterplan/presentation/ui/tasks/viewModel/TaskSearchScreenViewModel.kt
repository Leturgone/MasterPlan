package com.app.masterplan.presentation.ui.tasks.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.masterplan.domain.model.plans.Task
import com.app.masterplan.domain.model.plans.TaskStatus
import com.app.masterplan.domain.useacse.document.DownloadFileUseCase
import com.app.masterplan.domain.useacse.employee.GetLocalEmpIdUseCase
import com.app.masterplan.domain.useacse.plans.ChangeTaskStatusUseCase
import com.app.masterplan.domain.useacse.plans.GetAssignedTasksUseCase
import com.app.masterplan.domain.useacse.plans.SearchAssignedTasksByTitleUseCase
import com.app.masterplan.domain.useacse.searchHistory.ClearSearchHistoryUseCase
import com.app.masterplan.domain.useacse.searchHistory.GetSearchHistoryUseCase
import com.app.masterplan.domain.useacse.searchHistory.SaveSearchHistoryUseCase
import com.app.masterplan.presentation.ui.common.MasterPlanState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class TaskSearchScreenViewModel @Inject constructor(
    private val getLocalEmpIdUseCase: GetLocalEmpIdUseCase,
    private val getAssignedTasksUseCase: GetAssignedTasksUseCase,
    private val getSearchHistoryUseCase: GetSearchHistoryUseCase,
    private val saveSearchHistoryUseCase: SaveSearchHistoryUseCase,
    private val clearSearchHistoryUseCase: ClearSearchHistoryUseCase,
    private val searchAssignedTasksByTitleUseCase: SearchAssignedTasksByTitleUseCase,
    private val downloadFileUseCase: DownloadFileUseCase,
    private val changeTaskStatusUseCase: ChangeTaskStatusUseCase,
): ViewModel() {

    private val _assignedTasksListFlow = MutableStateFlow<MasterPlanState<List<Task>>>(MasterPlanState.Waiting)

    val assignedTasksListFlow: StateFlow<MasterPlanState<List<Task>>> = _assignedTasksListFlow


    private val _searchHistoryFlow = MutableStateFlow<MasterPlanState<List<String>>>(MasterPlanState.Waiting)

    val searchHistoryFlow: StateFlow<MasterPlanState<List<String>>> = _searchHistoryFlow

    private val _isModalVisible = MutableStateFlow(false)
    val isModalVisible: StateFlow<Boolean> = _isModalVisible


    private val _selectedTask = MutableStateFlow<Task?>(null)
    val selectedTask: StateFlow<Task?> = _selectedTask

    private val _downloadFile = MutableStateFlow<MasterPlanState<File>>(MasterPlanState.Waiting)

    val downloadFile: StateFlow<MasterPlanState<File>> = _downloadFile


    private lateinit var employeeId: UUID

    init {
        viewModelScope.launch {
            getLocalEmpIdUseCase().onSuccess {
                employeeId = it
            }.onFailure {
                _assignedTasksListFlow.value = MasterPlanState.Failure(Exception(it.message))
            }
        }
    }


    fun openTaskTab(task: Task) = viewModelScope.launch {
        _isModalVisible.value = true
        _selectedTask.value = task
    }


    fun downloadTask() = viewModelScope.launch {
        _downloadFile.value = MasterPlanState.Loading

        val task = _selectedTask.value

        if (task == null || task.documentId == null){
            _downloadFile.value =  MasterPlanState.Waiting
            return@launch
        }else {
            val file = downloadFileUseCase(task.documentId).getOrElse {
                _downloadFile.value =  MasterPlanState.Failure(Exception(it.message))
                return@launch
            }
            _downloadFile.value = MasterPlanState.Success(file)
        }
    }

    fun getTaskInWork() = viewModelScope.launch {
        val task = _selectedTask.value
        if (task !=null){
            changeTaskStatusUseCase(task.id, TaskStatus.IN_PROGRESS).getOrElse {
                return@launch
            }
        }
    }

    fun loadAssignedTasks() = viewModelScope.launch {
        _assignedTasksListFlow.value =  MasterPlanState.Loading

        val list = getAssignedTasksUseCase(employeeId).getOrElse {
            _assignedTasksListFlow.value =  MasterPlanState.Failure(Exception(it.message))
            return@launch
        }

        _assignedTasksListFlow.value = MasterPlanState.Success(list)
    }


    fun search(query: String) = viewModelScope.launch {
        _assignedTasksListFlow.value = MasterPlanState.Loading

        val list = searchAssignedTasksByTitleUseCase(query,employeeId,).getOrElse {
            _assignedTasksListFlow.value =  MasterPlanState.Failure(Exception(it.message))
            return@launch
        }
        saveSearchHistoryUseCase(query)
        _assignedTasksListFlow.value = MasterPlanState.Success(list)
    }

    fun clearSearchHistory() = viewModelScope.launch {
        _searchHistoryFlow.value = MasterPlanState.Loading
        clearSearchHistoryUseCase().onSuccess {
            _searchHistoryFlow.value = MasterPlanState.Success(emptyList())
        }.onFailure {
            _searchHistoryFlow.value =  MasterPlanState.Failure(Exception(it.message))
        }
    }

    fun getSearchHistory() = viewModelScope.launch {
        _searchHistoryFlow.value = MasterPlanState.Loading
        val list = getSearchHistoryUseCase().getOrElse {
            _searchHistoryFlow.value =  MasterPlanState.Failure(Exception(it.message))
            return@launch
        }
        _searchHistoryFlow.value = MasterPlanState.Success(list)
    }


    fun closeRequestTab() = viewModelScope.launch {
        _isModalVisible.value = false
        _selectedTask.value = null
        _downloadFile.value = MasterPlanState.Waiting

    }
}