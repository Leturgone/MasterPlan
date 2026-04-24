package com.app.masterplan.presentation.ui.tasks.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.masterplan.domain.model.employee.Employee
import com.app.masterplan.domain.model.plans.Plan
import com.app.masterplan.domain.model.plans.Task
import com.app.masterplan.domain.model.plans.TaskStatus
import com.app.masterplan.domain.model.userManagement.UserRole
import com.app.masterplan.domain.useacse.auth.GetUserRoleUseCase
import com.app.masterplan.domain.useacse.document.DownloadFileUseCase
import com.app.masterplan.domain.useacse.employee.GetEmployeeByIdUseCase
import com.app.masterplan.domain.useacse.plans.ChangeTaskStatusUseCase
import com.app.masterplan.domain.useacse.plans.DeleteTaskFromPlanUseCase
import com.app.masterplan.domain.useacse.plans.ExportPlanUseCase
import com.app.masterplan.domain.useacse.plans.FilterPlanTasksByStatusUseCase
import com.app.masterplan.domain.useacse.plans.GetPlanInfUseCase
import com.app.masterplan.domain.useacse.plans.GetTasksFromPlanUseCase
import com.app.masterplan.domain.useacse.plans.SortPlanTasksByEndDateUseCase
import com.app.masterplan.presentation.ui.common.MasterPlanState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class TasksFromPlanScreenViewModel @Inject constructor(
    private val getUserRoleUseCase: GetUserRoleUseCase,
    private val getTasksFromPlanUseCase: GetTasksFromPlanUseCase,
    private val sortPlanTasksByEndDateUseCase: SortPlanTasksByEndDateUseCase,
    private val filterPlanTasksByStatusUseCase: FilterPlanTasksByStatusUseCase,
    private val downloadFileUseCase: DownloadFileUseCase,
    private val exportPlanUseCase: ExportPlanUseCase,
    private val deleteTaskFromPlanUseCase: DeleteTaskFromPlanUseCase,
    private val getEmployeeByIdUseCase: GetEmployeeByIdUseCase,
    private val getPlanInfUseCase: GetPlanInfUseCase,
    private val changeTaskStatusUseCase: ChangeTaskStatusUseCase
): ViewModel() {

    private val _assignedTasksListFlow = MutableStateFlow<MasterPlanState<List<Task>>>(MasterPlanState.Waiting)

    val assignedTasksListFlow: StateFlow<MasterPlanState<List<Task>>> = _assignedTasksListFlow

    private val _currentTab = MutableStateFlow<Int>(0)

    val currentTab: StateFlow<Int> = _currentTab

    private val _currentPlan = MutableStateFlow<UUID?>(null)

    val currentPlan: StateFlow<UUID?> = _currentPlan

    private val _isModalVisible = MutableStateFlow(false)
    val isModalVisible: StateFlow<Boolean> = _isModalVisible


    private val _isModalPlanVisible = MutableStateFlow(false)
    val isModalPlanVisible: StateFlow<Boolean> = _isModalPlanVisible

    private val _loadedPlan = MutableStateFlow<Plan?>(null)
    val loadedPlan: StateFlow<Plan?> = _loadedPlan

    private val _downloadPlanFile = MutableStateFlow<MasterPlanState<File>>(MasterPlanState.Waiting)

    val downloadPlanFile: StateFlow<MasterPlanState<File>> = _downloadPlanFile

    private val _selectedTask = MutableStateFlow<Task?>(null)
    val selectedTask: StateFlow<Task?> = _selectedTask

    private val _downloadFile = MutableStateFlow<MasterPlanState<File>>(MasterPlanState.Waiting)

    val downloadFile: StateFlow<MasterPlanState<File>> = _downloadFile


    private val _exportFile = MutableStateFlow<MasterPlanState<File>>(MasterPlanState.Waiting)

    val exportFile: StateFlow<MasterPlanState<File>> = _exportFile

    private val _isCrudOperations = MutableStateFlow(false)
    val isCrudOperations: StateFlow<Boolean> = _isCrudOperations

    private val _executorsList = MutableStateFlow<MasterPlanState<List<Employee>>>(MasterPlanState.Waiting)
    val executorsList: StateFlow<MasterPlanState<List<Employee>>> = _executorsList


    init {
        viewModelScope.launch {
            getUserRoleUseCase().onSuccess { userRoles ->
                _isCrudOperations.value =  when {
                    UserRole.DIRECTOR in userRoles -> true
                    else -> false
                }
            }.onFailure {
                _assignedTasksListFlow.value = MasterPlanState.Failure(Exception(it.message))
            }
        }
    }

    fun setPlanId(id: String) = viewModelScope.launch {
        val uuid = UUID.fromString(id)
        _currentPlan.value = uuid
    }

    fun deleteTaskFromPlan() = viewModelScope.launch {
        if (!_isCrudOperations.value)  return@launch
        val task = _selectedTask.value ?: return@launch

        deleteTaskFromPlanUseCase(task.id).onSuccess {
            closeRequestTab()
            loadTasksFromPlan()
        }
    }

    fun completeTask() = viewModelScope.launch {
        if (!_isCrudOperations.value)  return@launch
        val task = _selectedTask.value ?: return@launch

        changeTaskStatusUseCase(task.id, TaskStatus.COMPLETED).onSuccess {
            closeRequestTab()
            loadTasksFromPlan()
        }
    }


    fun openTaskTab(task: Task) = viewModelScope.launch {
        _isModalVisible.value = true
        _selectedTask.value = task

        _executorsList.value = MasterPlanState.Loading
        val list = task.executorsIds.map {
            async { getEmployeeByIdUseCase(it).getOrNull() }
        }.awaitAll().filterNotNull()

        _executorsList.value = MasterPlanState.Success(list)
    }

    fun openPlanTab(planId: String) = viewModelScope.launch {
        val planUid = UUID.fromString(planId)
        val plan = getPlanInfUseCase(planUid).getOrElse {
            return@launch
        }
        _loadedPlan.value = plan
        _isModalPlanVisible.value = true
    }


    fun exportPlan() = viewModelScope.launch {
        val planId = _currentPlan.value ?: return@launch
        _exportFile.value = MasterPlanState.Loading

        val file = exportPlanUseCase(planId).getOrElse {
            _exportFile.value =  MasterPlanState.Failure(Exception(it.message))
            return@launch
        }
        _exportFile.value = MasterPlanState.Success(file)
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

    fun downloadPlan() = viewModelScope.launch {
        _downloadPlanFile.value = MasterPlanState.Loading

        val plan = _loadedPlan.value

        if (plan == null || plan.documentId == null){
            _downloadPlanFile.value =  MasterPlanState.Waiting
            return@launch
        }else {
            val file = downloadFileUseCase(plan.documentId).getOrElse {
                _downloadPlanFile.value =  MasterPlanState.Failure(Exception(it.message))
                return@launch
            }
            _downloadPlanFile.value = MasterPlanState.Success(file)
        }
    }


    fun loadTasksFromPlan() = viewModelScope.launch {
        val planId = _currentPlan.value ?: return@launch
        _assignedTasksListFlow.value =  MasterPlanState.Loading

        val list = getTasksFromPlanUseCase(planId).getOrElse {
            _assignedTasksListFlow.value =  MasterPlanState.Failure(Exception(it.message))
            return@launch
        }

        _assignedTasksListFlow.value = MasterPlanState.Success(list)
    }


    fun getCompletedTasks() = viewModelScope.launch {
        val planId = _currentPlan.value ?: return@launch
        _assignedTasksListFlow.value =  MasterPlanState.Loading
        val list = filterPlanTasksByStatusUseCase(planId, TaskStatus.COMPLETED).getOrElse {
            _assignedTasksListFlow.value =  MasterPlanState.Failure(Exception(it.message))
            return@launch
        }

        _assignedTasksListFlow.value = MasterPlanState.Success(list)
    }

    fun getInProgressTasks() = viewModelScope.launch {
        val planId = _currentPlan.value ?: return@launch
        _assignedTasksListFlow.value =  MasterPlanState.Loading
        val list = filterPlanTasksByStatusUseCase(planId, TaskStatus.IN_PROGRESS).getOrElse {
            _assignedTasksListFlow.value =  MasterPlanState.Failure(Exception(it.message))
            return@launch
        }

        _assignedTasksListFlow.value = MasterPlanState.Success(list)
    }



    fun getNotStartedTasks() = viewModelScope.launch {
        val planId = _currentPlan.value ?: return@launch
        _assignedTasksListFlow.value =  MasterPlanState.Loading
        val list = filterPlanTasksByStatusUseCase(planId, TaskStatus.NOT_STARTED).getOrElse {
            _assignedTasksListFlow.value =  MasterPlanState.Failure(Exception(it.message))
            return@launch
        }

        _assignedTasksListFlow.value = MasterPlanState.Success(list)
    }

    fun loadByCurrentSort() = viewModelScope.launch {

        val planId = _currentPlan.value ?: return@launch
        when(_currentTab.value){
            0 -> {
                loadTasksFromPlan()
            }
            1 -> {
                _assignedTasksListFlow.value =  MasterPlanState.Loading

                val list = sortPlanTasksByEndDateUseCase(planId).getOrElse {
                    _assignedTasksListFlow.value =  MasterPlanState.Failure(Exception(it.message))
                    return@launch
                }

                _assignedTasksListFlow.value = MasterPlanState.Success(list)
            }
        }
    }

    fun updateTab(index: Int) = viewModelScope.launch {
        _currentTab.value = index
    }


    fun closeRequestTab() = viewModelScope.launch {
        _isModalVisible.value = false
        _isModalPlanVisible.value = false
        _selectedTask.value = null
        _downloadFile.value = MasterPlanState.Waiting

    }

}