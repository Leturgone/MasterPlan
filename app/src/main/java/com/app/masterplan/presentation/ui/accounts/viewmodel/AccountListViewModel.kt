package com.app.masterplan.presentation.ui.accounts.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.masterplan.domain.model.employee.Employee
import com.app.masterplan.domain.useacse.employee.GetAllEmployeesUseCase
import com.app.masterplan.domain.useacse.employee.SearchEmployeeByNameUseCase
import com.app.masterplan.domain.useacse.searchHistory.ClearSearchHistoryUseCase
import com.app.masterplan.domain.useacse.searchHistory.GetSearchHistoryUseCase
import com.app.masterplan.domain.useacse.searchHistory.SaveSearchHistoryUseCase
import com.app.masterplan.presentation.ui.common.MasterPlanState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountListViewModel @Inject constructor(
    private val getAllEmployeesUseCase: GetAllEmployeesUseCase,
    private val getSearchHistoryUseCase: GetSearchHistoryUseCase,
    private val saveSearchHistoryUseCase: SaveSearchHistoryUseCase,
    private val clearSearchHistoryUseCase: ClearSearchHistoryUseCase,
    private val searchEmployeeByNameUseCase: SearchEmployeeByNameUseCase
): ViewModel() {

    private val _accountsListFlow = MutableStateFlow<MasterPlanState<List<Employee>>>(MasterPlanState.Waiting)

    val accountListFlow: StateFlow<MasterPlanState<List<Employee>>> = _accountsListFlow

    private val _searchHistoryFlow = MutableStateFlow<MasterPlanState<List<String>>>(MasterPlanState.Waiting)

    val searchHistoryFlow: StateFlow<MasterPlanState<List<String>>> = _searchHistoryFlow


    fun loadAccounts() = viewModelScope.launch{
        _accountsListFlow.value = MasterPlanState.Loading

        val list = getAllEmployeesUseCase().getOrElse {
            _accountsListFlow.value =  MasterPlanState.Failure(Exception(it.message))
            return@launch
        }
        _accountsListFlow.value = MasterPlanState.Success(list)
    }

    fun search(query: String) = viewModelScope.launch {
        _accountsListFlow.value = MasterPlanState.Loading

        val list = searchEmployeeByNameUseCase(query).getOrElse {
            _accountsListFlow.value =  MasterPlanState.Failure(Exception(it.message))
            return@launch
        }
        saveSearchHistoryUseCase(query)
        _accountsListFlow.value = MasterPlanState.Success(list)
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


    override fun onCleared() {
        super.onCleared()
        Log.d("AccountListViewModel", "=== AccountListViewModel CLEARED: ${this.hashCode()} ===")
        Log.d("AccountListViewModel", "Final state: ${_accountsListFlow.value}")
    }
}