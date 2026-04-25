package com.app.masterplan.presentation.ui.plans.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.app.masterplan.R
import com.app.masterplan.domain.model.plans.Plan
import com.app.masterplan.domain.model.plans.PlanStatus
import com.app.masterplan.presentation.ui.common.CardButton
import com.app.masterplan.presentation.ui.theme.GreenSoft
import com.app.masterplan.presentation.ui.theme.RedSoft
import com.app.masterplan.presentation.ui.theme.YellowSoft
import java.util.UUID

@Composable
fun PlanCard(
    plan: Plan,
    downloadButtonTitle: String,
    onDownloadFileClick: (UUID) -> Unit,
    onTasksClick: (UUID) -> Unit,
    crud: Boolean,
    onDeleteClick: (UUID) -> Unit,
    onStartClick: (UUID) -> Unit,
    onCreateReportClick: (UUID) -> Unit,
    onEditClick: (UUID) -> Unit,
    onCompleteClick: (UUID) -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize().padding(start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Box(Modifier.height(400.dp)) {
            PlanView(plan)
            FloatingActionButton(
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)
                    .align(Alignment.TopEnd)
                    .offset(y = 16.dp)
                    .padding(start = 16.dp, top = 16.dp),
                containerColor = GreenSoft,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                onClick = { onCompleteClick(plan.id) },
            ) {
                Icon(Icons.Filled.Check, "Floating complete button.")
            }
        }


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
                    onClick = { onDeleteClick(plan.id)  },
                ) {
                    Icon(Icons.Filled.Delete, "Floating delete button.")
                }

                when(plan.status){
                    PlanStatus.COMPLETED -> {}
                    PlanStatus.IN_PROGRESS -> {
                        FloatingActionButton(
                            modifier = Modifier.width(73.dp).height(56.dp),
                            containerColor = GreenSoft,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            onClick = { onCreateReportClick(plan.id) },
                        ) {
                            Icon(Icons.Filled.Mail, "Floating edit button.")
                        }
                    }
                    PlanStatus.NOT_STARTED -> {
                        FloatingActionButton(
                            modifier = Modifier.width(73.dp).height(56.dp),
                            containerColor = GreenSoft,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            onClick = { onStartClick(plan.id) },
                        ) {
                            Icon(Icons.Filled.AddCircle, "Floating edit button.")
                        }
                    }
                }


                FloatingActionButton(
                    modifier = Modifier.width(73.dp).height(56.dp),
                    containerColor = YellowSoft,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    onClick = { onEditClick(plan.id) },
                ) {
                    Icon(Icons.Filled.Edit, "Floating edit button.")
                }

            }
        }else{
            null
        }

    }
}