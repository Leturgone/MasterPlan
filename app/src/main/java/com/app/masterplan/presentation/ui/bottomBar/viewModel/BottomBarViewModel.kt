package com.app.masterplan.presentation.ui.bottomBar.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.masterplan.domain.model.userManagement.UserRole
import com.app.masterplan.domain.useacse.auth.GetUserRoleUseCase
import com.app.masterplan.presentation.ui.bottomBar.components.BottomNavigationItem
import com.app.masterplan.presentation.ui.common.MasterPlanState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomBarViewModel @Inject constructor(
    private val getUserRoleUseCase: GetUserRoleUseCase
): ViewModel() {

    private val _itemsFlow = MutableStateFlow<MasterPlanState<List<BottomNavigationItem>>>(
        MasterPlanState.Waiting
    )

    val itemsFlow: StateFlow<MasterPlanState<List<BottomNavigationItem>>> = _itemsFlow

    private var directorItems = listOf<BottomNavigationItem>()

    private var employeeItems = listOf<BottomNavigationItem>()


    private var adminItems = listOf<BottomNavigationItem>()


    fun setLists(
        directorItems: List<BottomNavigationItem>,
        employeeItems: List<BottomNavigationItem>,
        adminItems: List<BottomNavigationItem>
    ){
        this.directorItems = directorItems

        this.employeeItems = employeeItems


        this.adminItems = adminItems
    }


    fun loadItems() = viewModelScope.launch {
        _itemsFlow.value = MasterPlanState.Loading

        getUserRoleUseCase().onSuccess { userRoles ->
            val items = when {
                UserRole.DIRECTOR in userRoles -> directorItems
                UserRole.ADMIN in userRoles -> adminItems
                UserRole.EMPLOYEE in userRoles -> employeeItems
                else -> emptyList()
            }
            _itemsFlow.value = MasterPlanState.Success(items)
        }.onFailure {
            val exception = when(it){
                else -> Exception("Error during getting items: ${it.message}")
            }
            _itemsFlow.value = MasterPlanState.Failure(exception)
        }
    }
}