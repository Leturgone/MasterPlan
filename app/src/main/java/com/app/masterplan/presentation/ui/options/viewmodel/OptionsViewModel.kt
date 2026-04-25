package com.app.masterplan.presentation.ui.options.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.masterplan.domain.useacse.auth.LogoutUseCase
import com.app.masterplan.domain.useacse.theme.ChangeThemeUseCase
import com.app.masterplan.domain.useacse.theme.GetCurrentThemeIsDarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OptionsViewModel @Inject constructor(
    private val changeThemeUseCase: ChangeThemeUseCase,
    private val getCurrentThemeIsDarkUseCase: GetCurrentThemeIsDarkUseCase,
    private val logoutUseCase: LogoutUseCase
): ViewModel(){

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode:StateFlow<Boolean> = _isDarkMode

    private val _isLogout = MutableStateFlow(false)
    val isLogout:StateFlow<Boolean> = _isLogout

    init {
        viewModelScope.launch {
            _isDarkMode.value = getCurrentThemeIsDarkUseCase().getOrElse {
                false
            }
        }

    }


    fun switchTheme() = viewModelScope.launch {
        val currentTheme = _isDarkMode.value
        _isDarkMode.value = !currentTheme
        changeThemeUseCase(!currentTheme)
    }

    fun logout() = viewModelScope.launch {
        logoutUseCase().onSuccess {
            _isLogout.value = true
        }
    }
}