package com.app.masterplan.presentation.ui.employees.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.masterplan.domain.model.employee.Employee
import com.app.masterplan.presentation.ui.accounts.components.AccountListItemCard

@Composable
fun EmployeeList(
    employeeList: List<Employee>,
    onItemClick: (Employee) -> Unit
){
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(employeeList.size){
            val item = employeeList[it]
            Spacer(modifier = Modifier.height(30.dp))
            AccountListItemCard(item) {
                onItemClick(item)
            }
        }
    }
}