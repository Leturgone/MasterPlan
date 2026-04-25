package com.app.masterplan.presentation.ui.reports.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.masterplan.domain.dto.AttachedDocument
import com.app.masterplan.domain.dto.NewReportData
import com.app.masterplan.domain.model.reports.ReportType
import com.app.masterplan.domain.useacse.document.AttachFileUseCase
import com.app.masterplan.domain.useacse.employee.GetLocalEmpIdUseCase
import com.app.masterplan.domain.useacse.reports.CreateReportUseCase
import com.app.masterplan.presentation.ui.common.MasterPlanState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class NewReportViewModel @Inject constructor(
    private val getLocalEmpIdUseCase: GetLocalEmpIdUseCase,
    private val createNewReportUseCase: CreateReportUseCase,
    private val attachFileUseCase: AttachFileUseCase,
): ViewModel() {

    private lateinit var employeeId: UUID

    init {
        viewModelScope.launch {
            getLocalEmpIdUseCase().onSuccess {
                employeeId = it
            }.onFailure {
                return@launch
            }
        }
    }

    private val _currentCreatingReport = MutableStateFlow<NewReportData>(NewReportData(
        title = "",
        description = "",
        employeeId = UUID.randomUUID(),
        referenceId = UUID.randomUUID()))

    val currentCreatingReport: StateFlow<NewReportData> = _currentCreatingReport

    private val _attachedDocument = MutableStateFlow<MasterPlanState<AttachedDocument>>(
        MasterPlanState.Waiting
    )

    val attachedDocument: StateFlow<MasterPlanState<AttachedDocument>> = _attachedDocument

    private val _savingFlow = MutableStateFlow<MasterPlanState<UUID>>(MasterPlanState.Waiting)

    val savingFlow: StateFlow<MasterPlanState<UUID>> = _savingFlow

    fun attachDocument(uri: Uri?) = viewModelScope.launch {
        uri?: return@launch
        _attachedDocument.value = MasterPlanState.Loading
        val result = attachFileUseCase(uri.toString()).getOrElse {
            _attachedDocument.value =  MasterPlanState.Failure(Exception(it.message))
            return@launch
        }
        _attachedDocument.value = MasterPlanState.Success(result)
    }

    fun createNewReport(refId: String, type: ReportType) = viewModelScope.launch {
        _savingFlow.value = MasterPlanState.Loading
        val ref = UUID.fromString(refId)

        val reportData =_currentCreatingReport.value.copy(
            referenceId = ref, employeeId = employeeId
        )

        val document = attachedDocument.value as? MasterPlanState.Success?

        if (document == null){
            _savingFlow.value =  MasterPlanState.Failure(Exception("Attach document"))
            return@launch
        }

        val result = createNewReportUseCase(
            newReportData = reportData,
            document = document.result,
            reportType = type
        ).getOrElse {
            _savingFlow.value =  MasterPlanState.Failure(Exception(it.message))
            return@launch
        }
        _savingFlow.value = MasterPlanState.Success(result)
    }


    fun updateTitle(title: String) = viewModelScope.launch {
        _currentCreatingReport.value = _currentCreatingReport.value.copy(title = title)
    }

    fun updateDescription(description: String) = viewModelScope.launch {
        val desc = if (description == "") null else description
        _currentCreatingReport.value = _currentCreatingReport.value.copy(description = desc)
    }

}