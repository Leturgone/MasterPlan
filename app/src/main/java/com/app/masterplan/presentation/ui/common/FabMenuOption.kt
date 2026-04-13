package com.app.masterplan.presentation.ui.common

import androidx.compose.ui.graphics.vector.ImageVector

data class FabMenuOption(
    val title: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)
