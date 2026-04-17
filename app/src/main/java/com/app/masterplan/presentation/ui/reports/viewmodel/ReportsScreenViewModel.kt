package com.app.masterplan.presentation.ui.reports.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.masterplan.domain.model.reports.ReportStatus
import com.app.masterplan.domain.model.reports.ReportType
import com.app.masterplan.domain.model.userManagement.UserRole
import com.app.masterplan.domain.useacse.auth.GetUserRoleUseCase
import com.app.masterplan.domain.useacse.document.DownloadFileUseCase
import com.app.masterplan.domain.useacse.employee.GetEmployeeByIdUseCase
import com.app.masterplan.domain.useacse.employee.GetLocalEmpIdUseCase
import com.app.masterplan.domain.useacse.plans.GetPlanInfUseCase
import com.app.masterplan.domain.useacse.plans.GetTaskInfUseCase
import com.app.masterplan.domain.useacse.reports.ChangeReportStatusUseCase
import com.app.masterplan.domain.useacse.reports.DeleteReportUseCase
import com.app.masterplan.domain.useacse.reports.FilterByStatusCreatedReportsUseCase
import com.app.masterplan.domain.useacse.reports.FilterByStatusSubordinatesTaskReportsUseCase
import com.app.masterplan.domain.useacse.reports.GetCreatedReportsUseCase
import com.app.masterplan.domain.useacse.reports.GetSubordinatesTaskReportsUseCase
import com.app.masterplan.presentation.ui.common.MasterPlanState
import com.app.masterplan.presentation.ui.reports.components.ReportListDataItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ReportsScreenViewModel @Inject constructor(
    private val getLocalEmpIdUseCase: GetLocalEmpIdUseCase,
    private val getUserRoleUseCase: GetUserRoleUseCase,
    private val getEmployeeByIdUseCase: GetEmployeeByIdUseCase,
    private val changeReportStatusUseCase: ChangeReportStatusUseCase,
    private val deleteReportUseCase: DeleteReportUseCase,
    private val filterByStatusCreatedReportsUseCase: FilterByStatusCreatedReportsUseCase,
    private val filterByStatusSubordinatesTaskReportsUseCase: FilterByStatusSubordinatesTaskReportsUseCase,
    private val getCreatedReportsUseCase: GetCreatedReportsUseCase,
    private val getSubordinatesTaskReportsUseCase: GetSubordinatesTaskReportsUseCase,
    private val downloadFileUseCase: DownloadFileUseCase,
    private val getTaskInfUseCase: GetTaskInfUseCase,
    private val getPlanInfUseCase: GetPlanInfUseCase
): ViewModel() {

    private val _reportsListFlow = MutableStateFlow<MasterPlanState<List<ReportListDataItem>>>(MasterPlanState.Waiting)

    val reportsListFlow: StateFlow<MasterPlanState<List<ReportListDataItem>>> = _reportsListFlow

    private val _currentTab = MutableStateFlow<Int>(0)

    val currentTab: StateFlow<Int> = _currentTab


    // False - tasks, true - plans
    private val _isSwitchOn = MutableStateFlow(true)
    val isSwitchOn: StateFlow<Boolean> = _isSwitchOn

    private val _isSwitchVisible = MutableStateFlow(false)
    val isSwitchVisible: StateFlow<Boolean> = _isSwitchVisible

    private val _isModalVisible = MutableStateFlow(false)
    val isModalVisible: StateFlow<Boolean> = _isModalVisible

    private val _isSingleChoiceSegmentedButtonRowVisible = MutableStateFlow(false)

    val isSingleChoiceSegmentedButtonRowVisible: StateFlow<Boolean> = _isSingleChoiceSegmentedButtonRowVisible

    private val _selectedReport = MutableStateFlow<ReportListDataItem?>(null)
    val selectedReport: StateFlow<ReportListDataItem?> = _selectedReport

    private val _reportItemTitle = MutableStateFlow<MasterPlanState<String>>(MasterPlanState.Waiting)

    val reportItemTitle: StateFlow<MasterPlanState<String>> = _reportItemTitle


    private val _downloadFile = MutableStateFlow<MasterPlanState<File>>(MasterPlanState.Waiting)

    val downloadFile: StateFlow<MasterPlanState<File>> = _downloadFile



    private lateinit var employeeId: UUID

    init {
        viewModelScope.launch {
            getUserRoleUseCase().onSuccess {
                when{
                    UserRole.DIRECTOR in it ->{
                        _isSingleChoiceSegmentedButtonRowVisible.value = true
                        _isSwitchVisible.value = true
                    }
                }
            }.onFailure {
                _reportsListFlow.value = MasterPlanState.Failure(Exception(it.message))
            }
            getLocalEmpIdUseCase().onSuccess {
                employeeId = it
            }.onFailure {
                _reportsListFlow.value = MasterPlanState.Failure(Exception(it.message))
            }
        }
    }


    fun openReportTab(report: ReportListDataItem) = viewModelScope.launch {
        _isModalVisible.value = true
        _selectedReport.value = report
        _reportItemTitle.value = MasterPlanState.Loading
        val itemTitle = when (report.report.type) {
            ReportType.TASK ->{
                val task = getTaskInfUseCase(report.report.referenceId).getOrElse {
                    _reportItemTitle.value = MasterPlanState.Failure(Exception(it.message))
                    return@launch
                }
                task.title
            }
            ReportType.PLAN -> {
                val plan = getPlanInfUseCase(report.report.referenceId).getOrElse {
                    _reportItemTitle.value = MasterPlanState.Failure(Exception(it.message))
                    return@launch
                }
                plan.title
            }
        }

        _reportItemTitle.value = MasterPlanState.Success(itemTitle)
    }

    fun deleteReport() = viewModelScope.launch {
        val report = _selectedReport.value ?: return@launch
        deleteReportUseCase(report.report.id, report.report.type).getOrElse {
            return@launch
        }
    }

    fun downloadReport() = viewModelScope.launch {
        _downloadFile.value = MasterPlanState.Loading

        val report = _selectedReport.value

        if (report == null){
            _downloadFile.value =  MasterPlanState.Waiting
            return@launch
        }else {
            val file = downloadFileUseCase(report.report.documentId).getOrElse {
                _downloadFile.value =  MasterPlanState.Failure(Exception(it.message))
                return@launch
            }
            _downloadFile.value = MasterPlanState.Success(file)
        }
    }



    fun setReportInChecking() = viewModelScope.launch {
        val report = _selectedReport.value
        if (report!=null){
            changeReportStatusUseCase(
                reportId = report.report.id,
                status = ReportStatus.CHECKING,
                type = report.report.type
            )
        }
    }

    fun setReportInCheck() = viewModelScope.launch {
        val report = _selectedReport.value
        if (report!=null){
            changeReportStatusUseCase(
                reportId = report.report.id,
                status = ReportStatus.CHECKED,
                type = report.report.type
            )
        }
    }

    fun setReportToUpdate() = viewModelScope.launch {
        val report = _selectedReport.value
        if (report!=null){
            changeReportStatusUseCase(
                reportId = report.report.id,
                status = ReportStatus.TO_UPDATE,
                type = report.report.type
            )
        }
    }

    fun loadAllReports() = viewModelScope.launch {
        _reportsListFlow.value =  MasterPlanState.Loading

        val list = when(_currentTab.value){
            0 ->{
                val type = if (_isSwitchOn.value){
                    ReportType.TASK
                }else{
                    ReportType.PLAN
                }
                val reportList = getCreatedReportsUseCase(
                    employeeId = employeeId,
                    reportType = type
                ).getOrElse {
                    _reportsListFlow.value =  MasterPlanState.Failure(Exception(it.message))
                    return@launch
                }
                reportList.map { ReportListDataItem(report = it,) }

            }
            1 ->{
                val reportList = getSubordinatesTaskReportsUseCase(employeeId).getOrElse {
                    _reportsListFlow.value =  MasterPlanState.Failure(Exception(it.message))
                    return@launch
                }
                val employeeDeferreds = reportList.map { report ->
                    async {
                        getEmployeeByIdUseCase(report.employeeId).getOrElse { null }
                    }
                }

                val employees = employeeDeferreds.awaitAll()

                reportList.zip(employees) { report, emp ->
                    ReportListDataItem(
                        report = report,
                        employeeName = emp?.name,
                        employeeSurname = emp?.surname,
                        employeePatronymic = emp?.patronymic
                    )
                }
            }
            else -> {
                return@launch
            }
        }

        _reportsListFlow.value = MasterPlanState.Success(list)
    }

    fun getCheckedReports() = viewModelScope.launch {
        _reportsListFlow.value =  MasterPlanState.Loading

        val list = when(_currentTab.value){
            0 ->{
                val type = if (_isSwitchOn.value){
                    ReportType.TASK
                }else{
                    ReportType.PLAN
                }
                val reportList = filterByStatusCreatedReportsUseCase(
                    employeeId = employeeId,
                    status = ReportStatus.CHECKED,
                    reportType = type
                ).getOrElse {
                    _reportsListFlow.value =  MasterPlanState.Failure(Exception(it.message))
                    return@launch
                }

                reportList.map { ReportListDataItem(report = it,) }
            }
            1 ->{
                val reportList = filterByStatusSubordinatesTaskReportsUseCase(employeeId, ReportStatus.CHECKED).getOrElse {
                    _reportsListFlow.value =  MasterPlanState.Failure(Exception(it.message))
                    return@launch
                }

                val employeeDeferreds = reportList.map { report ->
                    async {
                        getEmployeeByIdUseCase(report.employeeId).getOrElse { null }
                    }
                }

                val employees = employeeDeferreds.awaitAll()

                reportList.zip(employees) { report, emp ->
                    ReportListDataItem(
                        report = report,
                        employeeName = emp?.name,
                        employeeSurname = emp?.surname,
                        employeePatronymic = emp?.patronymic
                    )
                }
            }
            else -> {
                return@launch
            }
        }

        _reportsListFlow.value = MasterPlanState.Success(list)
    }

    fun getInCheckReports() = viewModelScope.launch {
        _reportsListFlow.value =  MasterPlanState.Loading

        val list = when(_currentTab.value){
            0 ->{
                val type = if (_isSwitchOn.value){
                    ReportType.TASK
                }else{
                    ReportType.PLAN
                }
                val reportList = filterByStatusCreatedReportsUseCase(
                    employeeId = employeeId,
                    status = ReportStatus.CHECKING,
                    reportType = type
                ).getOrElse {
                    _reportsListFlow.value =  MasterPlanState.Failure(Exception(it.message))
                    return@launch
                }
                reportList.map { ReportListDataItem(report = it,) }
            }
            1 -> {
                val reportList = filterByStatusSubordinatesTaskReportsUseCase(employeeId, ReportStatus.CHECKING).getOrElse {
                    _reportsListFlow.value =  MasterPlanState.Failure(Exception(it.message))
                    return@launch
                }

                val employeeDeferreds = reportList.map { report ->
                    async {
                        getEmployeeByIdUseCase(report.employeeId).getOrElse { null }
                    }
                }

                val employees = employeeDeferreds.awaitAll()

                reportList.zip(employees) { report, emp ->
                    ReportListDataItem(
                        report = report,
                        employeeName = emp?.name,
                        employeeSurname = emp?.surname,
                        employeePatronymic = emp?.patronymic
                    )
                }
            }
            else -> {
                return@launch
            }
        }

        _reportsListFlow.value = MasterPlanState.Success(list)
    }

    fun getNotCheckedReports() = viewModelScope.launch {
        _reportsListFlow.value =  MasterPlanState.Loading

        val list = when(_currentTab.value){
            0 ->{
                val type = if (_isSwitchOn.value){
                    ReportType.TASK
                }else{
                    ReportType.PLAN
                }
                val reportList = filterByStatusCreatedReportsUseCase(
                    employeeId = employeeId,
                    status = ReportStatus.NOT_CHECKED,
                    reportType = type
                ).getOrElse {
                    _reportsListFlow.value =  MasterPlanState.Failure(Exception(it.message))
                    return@launch
                }

                reportList.map { ReportListDataItem(report = it,) }
            }
            1 -> {
                val reportList = filterByStatusSubordinatesTaskReportsUseCase(employeeId, ReportStatus.NOT_CHECKED).getOrElse {
                    _reportsListFlow.value =  MasterPlanState.Failure(Exception(it.message))
                    return@launch
                }

                val employeeDeferreds = reportList.map { report ->
                    async {
                        getEmployeeByIdUseCase(report.employeeId).getOrElse { null }
                    }
                }

                val employees = employeeDeferreds.awaitAll()

                reportList.zip(employees) { report, emp ->
                    ReportListDataItem(
                        report = report,
                        employeeName = emp?.name,
                        employeeSurname = emp?.surname,
                        employeePatronymic = emp?.patronymic
                    )
                }
            }
            else -> {
                return@launch
            }
        }

        _reportsListFlow.value = MasterPlanState.Success(list)
    }

    fun getToUpdateReports() = viewModelScope.launch {
        _reportsListFlow.value =  MasterPlanState.Loading

        val list = when(_currentTab.value){
            0 ->{
                val type = if (_isSwitchOn.value){
                    ReportType.TASK
                }else{
                    ReportType.PLAN
                }
                val reportList = filterByStatusCreatedReportsUseCase(
                    employeeId = employeeId,
                    status = ReportStatus.TO_UPDATE,
                    reportType = type
                ).getOrElse {
                    _reportsListFlow.value =  MasterPlanState.Failure(Exception(it.message))
                    return@launch
                }

                reportList.map { ReportListDataItem(report = it,) }
            }
            1 -> {
                val reportList = filterByStatusSubordinatesTaskReportsUseCase(employeeId, ReportStatus.TO_UPDATE).getOrElse {
                    _reportsListFlow.value =  MasterPlanState.Failure(Exception(it.message))
                    return@launch
                }

                val employeeDeferreds = reportList.map { report ->
                    async {
                        getEmployeeByIdUseCase(report.employeeId).getOrElse { null }
                    }
                }

                val employees = employeeDeferreds.awaitAll()

                reportList.zip(employees) { report, emp ->
                    ReportListDataItem(
                        report = report,
                        employeeName = emp?.name,
                        employeeSurname = emp?.surname,
                        employeePatronymic = emp?.patronymic
                    )
                }
            }
            else -> {
                return@launch
            }
        }

        _reportsListFlow.value = MasterPlanState.Success(list)
    }


    fun updateTab(index: Int) = viewModelScope.launch {
        _currentTab.value = index
        _isSwitchVisible.value = index == 0
        loadAllReports()
    }

    fun updateSwitcher() = viewModelScope.launch {
        _isSwitchOn.value = !_isSwitchOn.value
        loadAllReports()
    }


    fun closeRequestTab() = viewModelScope.launch {
        _isModalVisible.value = false
        _selectedReport.value = null
        _downloadFile.value = MasterPlanState.Waiting

    }

}