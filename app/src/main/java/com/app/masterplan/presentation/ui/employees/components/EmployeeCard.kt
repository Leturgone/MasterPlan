package com.app.masterplan.presentation.ui.employees.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.masterplan.R
import com.app.masterplan.presentation.ui.common.MasterPlanState
import com.app.masterplan.presentation.ui.employees.viewmodel.EmployeeCardViewModel
import com.app.masterplan.presentation.ui.tasks.components.TaskListItem

@Composable
fun EmployeeCard(viewModel: EmployeeCardViewModel){

    val employeeData = viewModel.employeeDataFlow.collectAsState()

    val taskDataFlow = viewModel.currentTaskFlow.collectAsState()

    Column(
        modifier = Modifier.height(500.dp).padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {

        when(employeeData.value){
            is MasterPlanState.Failure -> Text(
                stringResource(R.string.error_while_loading)
            )
            MasterPlanState.Loading -> CircularProgressIndicator()
            is MasterPlanState.Success -> {
                val employee = (employeeData.value as MasterPlanState.Success).result
                EmployeeView(employee)

                when(taskDataFlow.value) {
                    is MasterPlanState.Failure -> Text(
                        stringResource(R.string.error_while_loading)
                    )
                    MasterPlanState.Loading -> CircularProgressIndicator()
                    is MasterPlanState.Success -> {
                        Text(
                            text = "${stringResource(R.string.current_tasks)}: " ,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.End,
                            modifier = Modifier.padding(start = 16.dp, bottom = 10.dp)
                        )
                        Spacer(Modifier.height(10.dp))
                        val task = (taskDataFlow.value as MasterPlanState.Success).result
                        TaskListItem(
                            task = task,
                            employeeName = employee.name,
                            employeeSurname = employee.surname,
                            employeePatronymic = employee.patronymic,
                            onClick = {}
                        )
                    }
                    MasterPlanState.Waiting -> null
                }


            }
            MasterPlanState.Waiting -> null
        }

    }

}