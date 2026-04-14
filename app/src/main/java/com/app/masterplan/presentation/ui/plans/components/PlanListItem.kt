package com.app.masterplan.presentation.ui.plans.components

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
import com.app.masterplan.domain.model.plans.Plan
import com.app.masterplan.domain.model.plans.PlanStatus
import com.app.masterplan.domain.model.plans.TaskStatus
import com.app.masterplan.presentation.ui.theme.GreenSoft
import com.app.masterplan.presentation.ui.theme.RedSoft
import com.app.masterplan.presentation.ui.theme.YellowSoft

@Composable
fun PlanListItem(
    plan: Plan,
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
                text = plan.title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.TopStart).padding(10.dp)
            )

            Column(
                modifier = Modifier.fillMaxHeight().padding(horizontal = 16.dp).align(Alignment.TopEnd),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                val statusColor = when(plan.status){
                    PlanStatus.COMPLETED -> GreenSoft
                    PlanStatus.IN_PROGRESS -> YellowSoft
                    PlanStatus.NOT_STARTED -> RedSoft
                }

                Icon(
                    imageVector = Icons.Filled.Circle,
                    contentDescription ="StatusIcon",
                    tint = statusColor,
                    modifier = Modifier
                        .padding(top = 10.dp).align(alignment = Alignment.End)
                )
            }

        }

    }
}