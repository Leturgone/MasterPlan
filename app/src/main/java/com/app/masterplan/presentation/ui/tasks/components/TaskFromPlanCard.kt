package com.app.masterplan.presentation.ui.tasks.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.app.masterplan.domain.model.employee.Employee
import com.app.masterplan.domain.model.plans.Task
import com.app.masterplan.presentation.ui.common.CardButton
import com.app.masterplan.presentation.ui.theme.GreenSoft
import com.app.masterplan.presentation.ui.theme.RedSoft
import com.app.masterplan.presentation.ui.theme.YellowSoft
import java.util.UUID

@Composable
fun TaskFromPlanCard(
    task: Task,
    downloadButtonTitle: String,
    onDownloadFileClick: (UUID) -> Unit,
    executors: List<Employee>,
    crud: Boolean,
    onDeleteClick: (UUID) -> Unit,
    onEditClick: (UUID) -> Unit,
    onCompleteClick: (UUID) -> Unit,
){
    Column(
        modifier = Modifier.fillMaxSize().padding(start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.Start
    ) {
        TaskFromPlanView(task)
        Log.d("Composable", "isCrud value: $crud")

        if (task.documentId!= null){
            CardButton(downloadButtonTitle) { onDownloadFileClick(task.documentId) }
        }

        Text(
            text = "${stringResource(R.string.employees)}: " ,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 16.dp)
        )

        ExecutorsList(executors)

        if (crud){
            Row(
                Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                FloatingActionButton(
                    modifier = Modifier.width(73.dp).height(56.dp),
                    containerColor = RedSoft,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    onClick = { onDeleteClick(task.id) },
                ) {
                    Icon(Icons.Filled.Delete, "Floating delete button.")
                }

                FloatingActionButton(
                    modifier = Modifier.width(73.dp).height(56.dp),
                    containerColor = GreenSoft,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    onClick = { onCompleteClick(task.id) },
                ) {
                    Icon(Icons.Filled.Check, "Floating complete button.")
                }

                FloatingActionButton(
                    modifier = Modifier.width(73.dp).height(56.dp),
                    containerColor = YellowSoft,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    onClick = { onEditClick(task.id) },
                ) {
                    Icon(Icons.Filled.Edit, "Floating edit button.")
                }

            }
        }else{
            null
        }

    }
}