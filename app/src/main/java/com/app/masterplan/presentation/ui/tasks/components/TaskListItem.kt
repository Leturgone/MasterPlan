package com.app.masterplan.presentation.ui.tasks.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.masterplan.domain.model.plans.Task
import com.app.masterplan.domain.model.plans.TaskStatus
import com.app.masterplan.presentation.ui.common.AvatarNameSec
import com.app.masterplan.presentation.ui.theme.GreenSoft
import com.app.masterplan.presentation.ui.theme.RedSoft
import com.app.masterplan.presentation.ui.theme.YellowSoft

@Composable
fun TaskListItem(
    task: Task,
    employeeName: String? = null,
    employeeSurname: String? = null,
    employeePatronymic: String? = null,
    onClick: () -> Unit)
{
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
        ){
            Text(
                text = task.title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.TopStart).padding(10.dp)
            )

            Column(
                modifier = Modifier.fillMaxHeight().padding(horizontal = 16.dp).align(Alignment.TopEnd),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                val statusColor = when(task.status){
                    TaskStatus.COMPLETED -> GreenSoft
                    TaskStatus.IN_PROGRESS -> YellowSoft
                    TaskStatus.NOT_STARTED -> RedSoft
                }

                Icon(
                    imageVector = Icons.Filled.Circle,
                    contentDescription ="StatusIcon",
                    tint = statusColor,
                    modifier = Modifier
                        .padding(top = 10.dp).align(alignment = Alignment.End)
                )

                if (employeeName!=null && employeeSurname!=null){
                    val initials = "${employeeSurname[0]}${employeeName[0]}"
                    val nameWithInitials = "$employeeSurname ${employeeName[0]}.${employeePatronymic?.get(0)?:""}"
                    AvatarNameSec(
                        initials, nameWithInitials,Modifier.padding(top = 10.dp)
                    )
                }
            }

        }

    }
}