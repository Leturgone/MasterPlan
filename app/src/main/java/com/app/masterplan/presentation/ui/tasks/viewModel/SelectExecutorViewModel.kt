package com.app.masterplan.presentation.ui.tasks.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.masterplan.domain.model.employee.Employee
import com.app.masterplan.domain.useacse.employee.GetAllDirectorEmployeesUseCase
import com.app.masterplan.domain.useacse.employee.GetDirEmployeesWithoutTasksUseCase
import com.app.masterplan.domain.useacse.employee.GetLocalEmpIdUseCase
import com.app.masterplan.domain.useacse.employee.SearchDirEmployeeByNameUseCase
import com.app.masterplan.domain.useacse.employee.SortDirEmployeesByRatingUseCase
import com.app.masterplan.domain.useacse.employee.SortDirEmployeesByWorkloadUseCase
import com.app.masterplan.domain.useacse.searchHistory.ClearSearchHistoryUseCase
import com.app.masterplan.domain.useacse.searchHistory.GetSearchHistoryUseCase
import com.app.masterplan.domain.useacse.searchHistory.SaveSearchHistoryUseCase
import com.app.masterplan.presentation.ui.common.MasterPlanState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SelectExecutorViewModel  @Inject constructor (
    private val getLocalEmpIdUseCase: GetLocalEmpIdUseCase,
    private val getAllDirectorEmployeesUseCase: GetAllDirectorEmployeesUseCase,
    private val sortDirEmployeesByRatingUseCase: SortDirEmployeesByRatingUseCase,
    private val sortDirEmployeesByWorkloadUseCase: SortDirEmployeesByWorkloadUseCase,
    private val getDirEmployeesWithoutTasksUseCase: GetDirEmployeesWithoutTasksUseCase,
    private val searchDirEmployeeByNameUseCase: SearchDirEmployeeByNameUseCase,
    private val getSearchHistoryUseCase: GetSearchHistoryUseCase,
    private val saveSearchHistoryUseCase: SaveSearchHistoryUseCase,
    private val clearSearchHistoryUseCase: ClearSearchHistoryUseCase,
): ViewModel() {
    private val _employeesListFlow = MutableStateFlow<MasterPlanState<List<Employee>>>(MasterPlanState.Waiting)

    val employeesFlow: StateFlow<MasterPlanState<List<Employee>>> = _employeesListFlow

    private val _searchHistoryFlow = MutableStateFlow<MasterPlanState<List<String>>>(MasterPlanState.Waiting)

    val searchHistoryFlow: StateFlow<MasterPlanState<List<String>>> = _searchHistoryFlow


    private lateinit var directorId: UUID

    init {
        viewModelScope.launch {
            getLocalEmpIdUseCase().onSuccess {
                directorId = it
            }.onFailure {
                _employeesListFlow.value = MasterPlanState.Failure(Exception(it.message))
            }
        }
    }

    fun loadDirEmployees() = viewModelScope.launch{
        _employeesListFlow.value = MasterPlanState.Loading

        val list = getAllDirectorEmployeesUseCase(directorId).getOrElse {
            _employeesListFlow.value =  MasterPlanState.Failure(Exception(it.message))
            return@launch
        }
        _employeesListFlow.value = MasterPlanState.Success(list)
    }


    fun sortDirEmployeesByRating() = viewModelScope.launch{
        _employeesListFlow.value = MasterPlanState.Loading

        val list = sortDirEmployeesByRatingUseCase(directorId).getOrElse {
            _employeesListFlow.value =  MasterPlanState.Failure(Exception(it.message))
            return@launch
        }
        _employeesListFlow.value = MasterPlanState.Success(list)
    }


    fun sortDirEmployeesByWorkload() = viewModelScope.launch{
        _employeesListFlow.value = MasterPlanState.Loading

        val list = sortDirEmployeesByWorkloadUseCase(directorId).getOrElse {
            _employeesListFlow.value =  MasterPlanState.Failure(Exception(it.message))
            return@launch
        }
        _employeesListFlow.value = MasterPlanState.Success(list)
    }


    fun getDirEmployeesWithoutTasks() = viewModelScope.launch{
        _employeesListFlow.value = MasterPlanState.Loading

        val list = getDirEmployeesWithoutTasksUseCase(directorId).getOrElse {
            _employeesListFlow.value =  MasterPlanState.Failure(Exception(it.message))
            return@launch
        }
        _employeesListFlow.value = MasterPlanState.Success(list)
    }


    fun search(query: String) = viewModelScope.launch {
        _employeesListFlow .value = MasterPlanState.Loading

        val list = searchDirEmployeeByNameUseCase(query, directorId).getOrElse {
            _employeesListFlow .value =  MasterPlanState.Failure(Exception(it.message))
            return@launch
        }
        saveSearchHistoryUseCase(query)
        _employeesListFlow .value = MasterPlanState.Success(list)
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
}