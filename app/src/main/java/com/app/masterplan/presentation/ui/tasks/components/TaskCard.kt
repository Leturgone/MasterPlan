package com.app.masterplan.presentation.ui.tasks.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.app.masterplan.domain.model.plans.Task
import com.app.masterplan.presentation.ui.common.CardButton
import java.util.UUID

@Composable
fun TaskCard(
    task: Task,
    onPlanClick: (UUID) -> Unit,
    downloadButtonTitle: String,
    onDownLoadButtonClick: (UUID) -> Unit,
    functionButtonTitle: String?,
    functionOnClick: (UUID) -> Unit,
) {

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.Start
    ) {
        TaskView(task = task) { onPlanClick(it) }

        if (task.documentId!= null){
            CardButton(downloadButtonTitle) { onDownLoadButtonClick(task.documentId) }
        }

        if(functionButtonTitle!=null) (
                CardButton(functionButtonTitle) { functionOnClick(task.id) }
        )else {
            null
        }

    }
}