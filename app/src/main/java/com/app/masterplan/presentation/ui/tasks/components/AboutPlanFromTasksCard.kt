package com.app.masterplan.presentation.ui.tasks.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.masterplan.domain.model.plans.Plan
import com.app.masterplan.presentation.ui.common.CardButton
import com.app.masterplan.presentation.ui.plans.components.PlanView
import java.util.UUID

@Composable
fun AboutPlanFromTasksCard(
    plan: Plan,
    downloadButtonTitle: String,
    onDownloadFileClick: (UUID) -> Unit,
){
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.Start
    ) {
        PlanView(plan)

        if (plan.documentId!= null){
            CardButton(downloadButtonTitle) { onDownloadFileClick(plan.documentId) }
        }
    }
}