package com.app.masterplan.presentation.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FabMenu(menuOptions: List<FabMenuOption>) {
    var expanded by remember { mutableStateOf(false) }


    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(
            shape = CircleShape,
            content =  {
                when(expanded){
                    true -> Icon(Icons.Default.Close, null)
                    false -> Icon(Icons.Default.Menu, null)
                }
            },
            onClick = { expanded = !expanded },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 50.dp, end = 40.dp),
            contentColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.primary
        )

        // меню
        if (expanded) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 120.dp, end = 40.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.End,


            ) {
                menuOptions.forEach { option ->
                    ExtendedFloatingActionButton(
                        shape = CircleShape,
                        onClick = {
                            option.onClick()
                            expanded = false
                        },
                        icon = { Icon(option.icon, "Extended floating action button: ${option.title}",
                            tint = MaterialTheme.colorScheme.primary) },
                        text = {
                            Text(
                                text = option.title,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .padding(horizontal = 10.dp, vertical = 10.dp)
                            )
                        },
                        contentColor = MaterialTheme.colorScheme.primary,
                        containerColor = MaterialTheme.colorScheme.onPrimary,
                    )

                }
            }
        }
    }
}