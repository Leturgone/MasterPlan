package com.app.masterplan.presentation.ui.plans.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.masterplan.domain.model.plans.Plan
import com.app.masterplan.domain.model.plans.Task
import com.app.masterplan.presentation.ui.tasks.components.TaskListItem

@Composable
fun PlanList(
    planList: List<Plan>,
    onItemClick: (Plan) -> Unit
){
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(planList.size){
            val item = planList[it]
            Spacer(modifier = Modifier.height(30.dp))
            PlanListItem(item) {
                onItemClick(item)
            }
        }
    }
}