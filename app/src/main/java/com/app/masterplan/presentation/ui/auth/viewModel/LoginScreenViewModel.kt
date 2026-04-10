package com.app.masterplan.presentation.ui.auth.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.masterplan.domain.model.auth.JwtToken
import com.app.masterplan.domain.useacse.auth.GetUserIdUseCase
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
    private val getUserIdUseCase: GetUserIdUseCase,
    private val getUserRoleUseCase: GetUserRoleUseCase
): ViewModel() {
    private val _loginFlow = MutableStateFlow<MasterPlanState<JwtToken>>(MasterPlanState.Waiting)

    val loginFlow: StateFlow<MasterPlanState<JwtToken>> = _loginFlow


    fun login(login: String, password: String) = viewModelScope.launch {
        _loginFlow.value = MasterPlanState.Loading

        val result = loginUseCase(login, password)

        result.onSuccess {
            getUserRoleUseCase()
            getUserIdUseCase()
            _loginFlow.value = MasterPlanState.Success(it)
        }.onFailure {
            val exception = when(it){
                else -> Exception("Error during login: ${it.message}")
            }
            _loginFlow.value = MasterPlanState.Failure(exception)
        }
    }

}