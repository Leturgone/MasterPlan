package com.app.masterplan.presentation.ui.plans.components

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
import com.app.masterplan.domain.model.plans.Plan
import com.app.masterplan.domain.model.plans.PlanStatus
import com.app.masterplan.presentation.ui.theme.GreenSoft
import com.app.masterplan.presentation.ui.theme.RedSoft
import com.app.masterplan.presentation.ui.theme.YellowSoft
import java.time.format.DateTimeFormatter

@Composable
fun PlanView(
    plan: Plan,
){
    val outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yy")
    Column(
        modifier = Modifier.height(400.dp).padding(start = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = plan.title,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Start,
        )

        val statusColor = when(plan.status){
            PlanStatus.COMPLETED -> GreenSoft
            PlanStatus.IN_PROGRESS -> YellowSoft
            PlanStatus.NOT_STARTED -> RedSoft
        }

        val statusTitle = when(plan.status){
            PlanStatus.NOT_STARTED -> stringResource(R.string.not_started)
            PlanStatus.IN_PROGRESS -> stringResource(R.string.inProgress)
            PlanStatus.COMPLETED -> stringResource(R.string.completed)
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
            text = plan.description ,
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
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.End,
            )

            Text(
                text = plan.endDate.format(outputFormatter),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Start,
            )


        }

    }

}