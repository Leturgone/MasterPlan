package com.app.masterplan.presentation.ui.auth.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.masterplan.domain.model.auth.JwtToken
import com.app.masterplan.domain.model.userManagement.UserRole
import com.app.masterplan.domain.useacse.auth.CheckIfAlreadyLoggedUseCase
import com.app.masterplan.domain.useacse.employee.GetLocalEmpIdUseCase
import com.app.masterplan.domain.useacse.auth.GetUserRoleUseCase
import com.app.masterplan.domain.useacse.auth.LoginUseCase
import com.app.masterplan.presentation.ui.common.MasterPlanState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val getLocalEmpIdUseCase: GetLocalEmpIdUseCase,
    private val getUserRoleUseCase: GetUserRoleUseCase,
    private val checkIfAlreadyLoggedUseCase: CheckIfAlreadyLoggedUseCase,
): ViewModel() {
    private val _loginFlow = MutableStateFlow<MasterPlanState<Boolean>>(MasterPlanState.Waiting)

    val loginFlow: StateFlow<MasterPlanState<Boolean>> = _loginFlow

    private val _isAdmin = MutableStateFlow<Boolean>(false)

    val isAdmin : StateFlow<Boolean> = _isAdmin

    init {
        viewModelScope.launch {
            _loginFlow.value = MasterPlanState.Loading
            checkIfAlreadyLoggedUseCase().onSuccess { isLogged ->
                if (!isLogged){
                    _loginFlow.value = MasterPlanState.Waiting
                }else {
                    getUserRoleUseCase().onSuccess { userRoles ->
                        _isAdmin.value =  when {
                            UserRole.ADMIN in userRoles -> true
                            else -> false
                        }
                        _loginFlow.value = MasterPlanState.Success(true)
                    }.onFailure {
                        _loginFlow.value = MasterPlanState.Waiting
                        _isAdmin.value = false
                    }
                }
            }.onFailure {
                _loginFlow.value = MasterPlanState.Waiting
            }
        }
    }


    fun login(login: String, password: String) = viewModelScope.launch {
        _loginFlow.value = MasterPlanState.Loading

        val result = loginUseCase(login, password)

        result.onSuccess {
            getUserRoleUseCase().onSuccess { userRoles ->
                _isAdmin.value =  when {
                    UserRole.ADMIN in userRoles -> true
                    else -> false
                }
            }
            getLocalEmpIdUseCase()
            _loginFlow.value = MasterPlanState.Success(true)
        }.onFailure {
            val exception = when(it){
                else -> Exception("Error during login: ${it.message}")
            }
            _loginFlow.value = MasterPlanState.Failure(exception)
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("LoginScreenViewModel", "=== LoginScreenViewModel CLEARED: ${this.hashCode()} ===")
        Log.d("LoginScreenViewModel", "Final state: ${_loginFlow.value}")
    }

}