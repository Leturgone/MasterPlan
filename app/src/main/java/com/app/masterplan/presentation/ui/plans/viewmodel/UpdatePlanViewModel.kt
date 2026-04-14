package com.app.masterplan.presentation.ui.plans.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.masterplan.domain.dto.AttachedDocument
import com.app.masterplan.domain.model.plans.Plan
import com.app.masterplan.domain.useacse.document.AttachFileUseCase
import com.app.masterplan.domain.useacse.plans.GetPlanInfUseCase
import com.app.masterplan.domain.useacse.plans.UpdatePlanUseCase
import com.app.masterplan.presentation.ui.common.MasterPlanState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class UpdatePlanViewModel  @Inject constructor(
    private val getPlanInfUseCase: GetPlanInfUseCase,
    private val updatePlanUseCase: UpdatePlanUseCase,
    private val attachFileUseCase: AttachFileUseCase
) : ViewModel() {

    private val _attachedDocument = MutableStateFlow<MasterPlanState<AttachedDocument>>(MasterPlanState.Waiting)
    val attachedDocument: StateFlow<MasterPlanState<AttachedDocument>> = _attachedDocument

    private val _savingFlow = MutableStateFlow<MasterPlanState<UUID>>(MasterPlanState.Waiting)
    val savingFlow: StateFlow<MasterPlanState<UUID>> = _savingFlow

    private val _loadingPlan = MutableStateFlow<MasterPlanState<Plan>>(MasterPlanState.Waiting)
    val loadingPlan: StateFlow<MasterPlanState<Plan>> = _loadingPlan

    private val _updatedPlan = MutableStateFlow<Plan?>(null)

    val updatedPlan: StateFlow<Plan?> = _updatedPlan

    fun loadPlan(planId: String) = viewModelScope.launch {
        _loadingPlan.value = MasterPlanState.Loading
        val planUid = UUID.fromString(planId)
        val result = getPlanInfUseCase(planUid).getOrElse {
            _loadingPlan.value = MasterPlanState.Failure(Exception(it.message))
            return@launch
        }
        _updatedPlan.value = result
        _loadingPlan.value = MasterPlanState.Success(result)
    }

    fun attachDocument(uri: Uri?) = viewModelScope.launch {
        uri ?: return@launch
        _attachedDocument.value = MasterPlanState.Loading
        val result = attachFileUseCase(uri.toString()).getOrElse {
            _attachedDocument.value = MasterPlanState.Failure(Exception(it.message))
            return@launch
        }
        _attachedDocument.value = MasterPlanState.Success(result)
    }

    fun updateTask() = viewModelScope.launch {
        _savingFlow.value = MasterPlanState.Loading

        val updatedPlan = _updatedPlan.value ?: return@launch

        val document = attachedDocument.value as? MasterPlanState.Success

        val result = updatePlanUseCase(
            updatedPlan.id,
            updatedPlan,
            document?.result
        ).getOrElse {
            _savingFlow.value = MasterPlanState.Failure(Exception(it.message))
            return@launch
        }
        _savingFlow.value = MasterPlanState.Success(result)
    }

    fun updateTitle(title: String) = viewModelScope.launch {
        _updatedPlan.value ?: return@launch
        _updatedPlan.value = _updatedPlan.value!!.copy(title = title)
    }

    fun updateDescription(description: String) = viewModelScope.launch {
        _updatedPlan.value ?: return@launch
        _updatedPlan.value = _updatedPlan.value!!.copy(description = description)
    }

    fun updateDate(date: LocalDate) = viewModelScope.launch {
        _updatedPlan.value ?: return@launch
        _updatedPlan.value = _updatedPlan.value!!.copy(endDate = date)
    }
}