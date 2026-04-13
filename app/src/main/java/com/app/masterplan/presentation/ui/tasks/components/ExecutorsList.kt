package com.app.masterplan.presentation.ui.tasks.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.masterplan.domain.model.employee.Employee
import com.app.masterplan.presentation.ui.common.AvatarNameSec

@Composable
fun ExecutorsList(
    list: List<Employee>
){
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(list.size){
            val employee = list[it]
            val initials = "${employee.surname[0]}${employee.name[0]}"
            val nameWithInitials = "${employee.surname} ${employee.name[0]}.${employee.patronymic?.get(0)?:""}"

            AvatarNameSec(
                initials = initials,
                surnameWithInitials = nameWithInitials,
                modifier = Modifier.padding(top = 10.dp)
            )
        }
    }
}