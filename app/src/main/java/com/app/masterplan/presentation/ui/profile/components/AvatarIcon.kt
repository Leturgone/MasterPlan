package com.app.masterplan.presentation.ui.profile.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AvatarIcon(initials: String, size: Dp = 40.dp ){
    Box(){
        Surface(
            shape = RoundedCornerShape(50),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.size(size),
            content = {
                Box(Modifier.fillMaxWidth(), Alignment.Center) {
                    Text(
                        text = initials,
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }

            })
    }
}