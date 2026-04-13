package com.app.masterplan.presentation.ui.tasks.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.masterplan.R
import com.app.masterplan.domain.model.plans.Task
import com.app.masterplan.domain.model.plans.TaskStatus
import com.app.masterplan.presentation.ui.theme.GreenSoft
import com.app.masterplan.presentation.ui.theme.RedSoft
import com.app.masterplan.presentation.ui.theme.YellowSoft
import java.time.format.DateTimeFormatter
import java.util.UUID

@Composable
fun TaskView(task: Task, onPlanClick: (UUID) -> Unit){


    val outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yy")
    Column(
        modifier = Modifier.height(400.dp).padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = task.title,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Start,
            )

            Text(
                text = stringResource(R.string.to_plan),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.End,
                modifier = Modifier.clickable(onClick = { onPlanClick(task.planId) })
            )
        }

        val statusColor = when(task.status){
            TaskStatus.COMPLETED -> GreenSoft
            TaskStatus.IN_PROGRESS -> YellowSoft
            TaskStatus.NOT_STARTED -> RedSoft
        }

        val statusTitle = when(task.status){
            TaskStatus.NOT_STARTED -> stringResource(R.string.not_started)
            TaskStatus.IN_PROGRESS -> stringResource(R.string.inProgress)
            TaskStatus.COMPLETED -> stringResource(R.string.completed)
        }

        Text(
            text = statusTitle,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = statusColor,
            textAlign = TextAlign.End
        )


        Text(
            text = stringResource(R.string.desc) ,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Start,
        )

        Text(
            text = task.description ,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black,
            textAlign = TextAlign.Start,
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {

            Text(
                text = "${stringResource(R.string.deadline)}: ",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.End,
            )

            Text(
                text = task.endDate.format(outputFormatter),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Start,
            )


        }

    }

}