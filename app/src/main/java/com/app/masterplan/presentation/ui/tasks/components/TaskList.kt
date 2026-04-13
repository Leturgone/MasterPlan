package com.app.masterplan.presentation.ui.tasks.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.masterplan.domain.model.plans.Task

@Composable
fun TaskList(
    taskList: List<Task>,
    onItemClick: (Task) -> Unit
){
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(taskList.size){
            val item = taskList[it]
            Spacer(modifier = Modifier.height(30.dp))
            TaskListItem(item) {
                onItemClick(item)
            }
        }
    }
}