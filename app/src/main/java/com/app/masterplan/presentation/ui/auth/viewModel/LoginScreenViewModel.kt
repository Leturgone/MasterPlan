package com.app.masterplan.presentation.ui.auth.viewModel

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
    private val _loginFlow = MutableStateFlow<MasterPlanState<JwtToken>>(MasterPlanState.Waiting)

    val loginFlow: StateFlow<MasterPlanState<JwtToken>> = _loginFlow

    private val _isAdmin = MutableStateFlow<Boolean>(false)

    val isAdmin : StateFlow<Boolean> = _isAdmin

    private val _isLogged = MutableStateFlow<MasterPlanState<Boolean>>(MasterPlanState.Waiting)

    val isLogged : StateFlow<MasterPlanState<Boolean>> = _isLogged


    init {
        viewModelScope.launch {
            _isLogged.value = MasterPlanState.Loading
            checkIfAlreadyLoggedUseCase().onSuccess {
                getUserRoleUseCase().onSuccess { userRoles ->
                    _isAdmin.value =  when {
                        UserRole.ADMIN in userRoles -> true
                        else -> false
                    }
                    _isLogged.value = MasterPlanState.Success(true)
                }.onFailure {
                    _isLogged.value = MasterPlanState.Failure(Exception())
                    _isAdmin.value = false
                }
            }.onFailure {
                _isLogged.value = MasterPlanState.Failure(Exception())
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
            _isLogged.value = MasterPlanState.Success(true)
            _loginFlow.value = MasterPlanState.Success(it)
        }.onFailure {
            val exception = when(it){
                else -> Exception("Error during login: ${it.message}")
            }
            _loginFlow.value = MasterPlanState.Failure(exception)
        }
    }

}