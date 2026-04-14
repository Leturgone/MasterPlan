package com.app.masterplan.presentation.ui.reports.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.masterplan.domain.dto.AttachedDocument
import com.app.masterplan.domain.dto.UpdateReportData
import com.app.masterplan.domain.model.reports.ReportType
import com.app.masterplan.domain.useacse.document.AttachFileUseCase
import com.app.masterplan.domain.useacse.employee.GetLocalEmpIdUseCase
import com.app.masterplan.domain.useacse.reports.GetReportInfUseCase
import com.app.masterplan.domain.useacse.reports.UpdateReportUseCase
import com.app.masterplan.presentation.ui.common.MasterPlanState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class UpdateReportViewModel @Inject constructor(
    private val getLocalEmpIdUseCase: GetLocalEmpIdUseCase,
    private val updateReportUseCase: UpdateReportUseCase,
    private val getReportInfUseCase: GetReportInfUseCase,
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

    private val _updatedReport = MutableStateFlow<UpdateReportData?>(null)

    val updatedReport: StateFlow<UpdateReportData?> = _updatedReport

    private val _attachedDocument = MutableStateFlow<MasterPlanState<AttachedDocument>>(
        MasterPlanState.Waiting
    )

    val attachedDocument: StateFlow<MasterPlanState<AttachedDocument>> = _attachedDocument

    private val _savingFlow = MutableStateFlow<MasterPlanState<UUID>>(MasterPlanState.Waiting)

    val savingFlow: StateFlow<MasterPlanState<UUID>> = _savingFlow


    fun loadReport(reportId: String, reportType: ReportType) = viewModelScope.launch{
        val reportUid = UUID.fromString(reportId)
        val result = getReportInfUseCase(reportUid,reportType).getOrElse {
            return@launch
        }
        _updatedReport.value = UpdateReportData(
            title = result.title,
            description = result.description,
            documentId = result.documentId
        )
    }


    fun attachDocument(uri: Uri?) = viewModelScope.launch {
        uri?: return@launch
        _attachedDocument.value = MasterPlanState.Loading
        val result = attachFileUseCase(uri.toString()).getOrElse {
            _attachedDocument.value =  MasterPlanState.Failure(Exception(it.message))
            return@launch
        }
        _attachedDocument.value = MasterPlanState.Success(result)
    }

    fun updateReport(reportId: String, type: ReportType) = viewModelScope.launch {
        _savingFlow.value = MasterPlanState.Loading
        val reportUid = UUID.fromString(reportId)

        val reportData = _updatedReport.value ?: return@launch

        val document = attachedDocument.value as? MasterPlanState.Success?

        if (document == null){
            _savingFlow.value =  MasterPlanState.Failure(Exception("Attach document"))
            return@launch
        }

        val result = updateReportUseCase(
            reportId = reportUid,
            updatedData = reportData,
            reportType = type,
            document = document.result
        ).getOrElse {
            _savingFlow.value =  MasterPlanState.Failure(Exception(it.message))
            return@launch
        }
        _savingFlow.value = MasterPlanState.Success(result)
    }


    fun updateTitle(title: String) = viewModelScope.launch {
        _updatedReport.value ?: return@launch
        _updatedReport.value = _updatedReport.value!!.copy(title = title)
    }

    fun updateDescription(description: String) = viewModelScope.launch {
        val desc = if (description == "") null else description
        _updatedReport.value ?: return@launch
        _updatedReport.value = _updatedReport.value!!.copy(description = desc)
    }

}