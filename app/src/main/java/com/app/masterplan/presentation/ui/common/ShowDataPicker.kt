package com.app.masterplan.presentation.ui.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.app.masterplan.R
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@Composable
fun DeadLintPicker(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    initialDate: LocalDate? = null
) {
    if (!isVisible) return

    // Инициализируем состояние DatePicker
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDate?.toEpochDay()?.times(86400000)
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    datePickerState.selectedDateMillis?.let { timestamp ->
                        // миллисекунды в LocalDate
                        val localDate = Instant.ofEpochMilli(timestamp)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        onDateSelected(localDate)
                    }
                }
            ){
                Text(stringResource(id = R.string.confirm))
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            modifier = Modifier.padding(16.dp)
        )
    }
}

