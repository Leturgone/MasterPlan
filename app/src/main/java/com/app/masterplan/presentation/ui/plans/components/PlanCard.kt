package com.app.masterplan.presentation.ui.plans.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.app.masterplan.R
import com.app.masterplan.domain.model.employee.Employee
import com.app.masterplan.domain.model.plans.Plan
import com.app.masterplan.presentation.ui.common.CardButton
import com.app.masterplan.presentation.ui.theme.RedSoft
import com.app.masterplan.presentation.ui.theme.YellowSoft
import java.util.UUID

@Composable
fun PlanCard(
    plan: Plan,
    downloadButtonTitle: String,
    onDownloadFileClick: (UUID) -> Unit,
    onTasksClick: (UUID) -> Unit,
    executors: List<Employee>,
    crud: Boolean,
    onDeleteClick: (UUID) -> Unit,
    onEditClick: (UUID) -> Unit
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

        CardButton(stringResource(R.string.tasks)) { onTasksClick(plan.id) }


        if (crud){
            Row(
                Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                FloatingActionButton(
                    modifier = Modifier.width(73.dp).height(56.dp),
                    containerColor = RedSoft,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    onClick = { onEditClick(plan.id) },
                ) {
                    Icon(Icons.Filled.Delete, "Floating delete button.")
                }

                FloatingActionButton(
                    modifier = Modifier.width(73.dp).height(56.dp),
                    containerColor = YellowSoft,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    onClick = { onDeleteClick(plan.id) },
                ) {
                    Icon(Icons.Filled.Edit, "Floating edit button.")
                }

            }
        }else{
            null
        }

    }
}