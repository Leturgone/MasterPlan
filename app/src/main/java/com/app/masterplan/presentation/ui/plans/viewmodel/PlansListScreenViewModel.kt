package com.app.masterplan.presentation.ui.plans.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.masterplan.domain.model.plans.Plan
import com.app.masterplan.domain.model.plans.PlanStatus
import com.app.masterplan.domain.model.userManagement.UserRole
import com.app.masterplan.domain.useacse.auth.GetUserRoleUseCase
import com.app.masterplan.domain.useacse.document.DownloadFileUseCase
import com.app.masterplan.domain.useacse.employee.GetLocalEmpIdUseCase
import com.app.masterplan.domain.useacse.plans.ChangePlanStatusUseCase
import com.app.masterplan.domain.useacse.plans.DeletePlanUseCase
import com.app.masterplan.domain.useacse.plans.FilterDirPlansByStatusUseCase
import com.app.masterplan.domain.useacse.plans.GetDirPlansUseCase
import com.app.masterplan.domain.useacse.plans.SortDirPlansByEndDateUseCase
import com.app.masterplan.presentation.ui.common.MasterPlanState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PlansListScreenViewModel @Inject constructor(
    private val getUserRoleUseCase: GetUserRoleUseCase,
    private val getLocalEmpIdUseCase: GetLocalEmpIdUseCase,
    private val getDirPlansUseCase: GetDirPlansUseCase,
    private val sortDirPlansByEndDateUseCase: SortDirPlansByEndDateUseCase,
    private val filterDirPlansByStatusUseCase: FilterDirPlansByStatusUseCase,
    private val downloadFileUseCase: DownloadFileUseCase,
    private val deletePlanUseCase: DeletePlanUseCase,
    private val changePlanStatusUseCase: ChangePlanStatusUseCase
): ViewModel() {

    private val _dirPlansListFlow = MutableStateFlow<MasterPlanState<List<Plan>>>(MasterPlanState.Waiting)

    val dirPlansListFlow: StateFlow<MasterPlanState<List<Plan>>> = _dirPlansListFlow

    private val _currentTab = MutableStateFlow<Int>(0)

    val currentTab: StateFlow<Int> = _currentTab



    private val _isModalVisible = MutableStateFlow(false)
    val isModalVisible: StateFlow<Boolean> = _isModalVisible


    private val _selectedPlan = MutableStateFlow<Plan?>(null)
    val selectedPlan: StateFlow<Plan?> = _selectedPlan

    private val _downloadFile = MutableStateFlow<MasterPlanState<File>>(MasterPlanState.Waiting)

    val downloadFile: StateFlow<MasterPlanState<File>> = _downloadFile


    private val _isCrudOperations = MutableStateFlow(false)
    val isCrudOperations: StateFlow<Boolean> = _isCrudOperations
    private lateinit var employeeId: UUID


    init {
        viewModelScope.launch {
            getLocalEmpIdUseCase().onSuccess {
                employeeId = it
            }.onFailure {
                _dirPlansListFlow.value = MasterPlanState.Failure(Exception(it.message))
            }
            getUserRoleUseCase().onSuccess { userRoles ->
                _isCrudOperations.value =  when {
                    UserRole.DIRECTOR in userRoles -> true
                    else -> false
                }
            }.onFailure {
                _dirPlansListFlow.value = MasterPlanState.Failure(Exception(it.message))
            }
        }
    }

    fun deletePlan() = viewModelScope.launch {
        if (!_isCrudOperations.value)  return@launch
        val plan = _selectedPlan.value ?: return@launch


        val result = deletePlanUseCase(plan.id).onSuccess {
            closeRequestTab()
            loadPlans()
        }


    }


    fun openPlanTab(plan: Plan) = viewModelScope.launch {
        _isModalVisible.value = true
        _selectedPlan.value = plan
    }


    fun downloadPlan() = viewModelScope.launch {
        _downloadFile.value = MasterPlanState.Loading

        val plan = _selectedPlan.value

        if (plan == null || plan.documentId == null){
            _downloadFile.value =  MasterPlanState.Waiting
            return@launch
        }else {
            val file = downloadFileUseCase(plan.documentId).getOrElse {
                _downloadFile.value =  MasterPlanState.Failure(Exception(it.message))
                return@launch
            }
            _downloadFile.value = MasterPlanState.Success(file)
        }
    }


    fun loadPlans() = viewModelScope.launch {

        _dirPlansListFlow.value =  MasterPlanState.Loading
        val list = getDirPlansUseCase(employeeId).getOrElse {
            _dirPlansListFlow.value =  MasterPlanState.Failure(Exception(it.message))
            return@launch
        }

        _dirPlansListFlow.value = MasterPlanState.Success(list)
    }


    fun getCompletedPlans() = viewModelScope.launch {
        _dirPlansListFlow.value =  MasterPlanState.Loading
        val list = filterDirPlansByStatusUseCase(employeeId, PlanStatus.COMPLETED).getOrElse {
            _dirPlansListFlow.value =  MasterPlanState.Failure(Exception(it.message))
            return@launch
        }

        _dirPlansListFlow.value = MasterPlanState.Success(list)
    }

    fun getInProgressTasks() = viewModelScope.launch {
        _dirPlansListFlow.value =  MasterPlanState.Loading
        val list = filterDirPlansByStatusUseCase(employeeId, PlanStatus.IN_PROGRESS).getOrElse {
            _dirPlansListFlow.value =  MasterPlanState.Failure(Exception(it.message))
            return@launch
        }

        _dirPlansListFlow.value = MasterPlanState.Success(list)
    }

    fun getNotStartedTasks() = viewModelScope.launch {
        _dirPlansListFlow.value =  MasterPlanState.Loading
        val list = filterDirPlansByStatusUseCase(employeeId, PlanStatus.NOT_STARTED).getOrElse {
            _dirPlansListFlow.value =  MasterPlanState.Failure(Exception(it.message))
            return@launch
        }

        _dirPlansListFlow.value = MasterPlanState.Success(list)
    }

    fun loadByCurrentSort() = viewModelScope.launch {
        when(_currentTab.value){
            0 -> {
                loadPlans()
            }
            1 -> {
                _dirPlansListFlow.value =  MasterPlanState.Loading

                val list = sortDirPlansByEndDateUseCase(employeeId).getOrElse {
                    _dirPlansListFlow.value =  MasterPlanState.Failure(Exception(it.message))
                    return@launch
                }

                _dirPlansListFlow.value = MasterPlanState.Success(list)
            }
        }
    }

    fun updateTab(index: Int) = viewModelScope.launch {
        _currentTab.value = index
    }


    fun closeRequestTab() = viewModelScope.launch {
        _isModalVisible.value = false
        _selectedPlan.value = null
        _downloadFile.value = MasterPlanState.Waiting

    }

    fun getPlanInWork() = viewModelScope.launch {
        if (!_isCrudOperations.value)  return@launch
        val planId = _selectedPlan.value?.id ?: return@launch
        changePlanStatusUseCase(planId, PlanStatus.IN_PROGRESS).onSuccess {
            closeRequestTab()
            loadPlans()
        }
    }

    fun completePlan() = viewModelScope.launch {
        if (!_isCrudOperations.value)  return@launch
        val planId = _selectedPlan.value?.id ?: return@launch
        changePlanStatusUseCase(planId, PlanStatus.COMPLETED).onSuccess {
            closeRequestTab()
            loadPlans()
        }
    }
}