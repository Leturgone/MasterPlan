package com.app.masterplan.presentation.ui.requests.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.masterplan.domain.model.adminRequests.AdminRequest
import com.app.masterplan.domain.model.adminRequests.AdminRequestStatus
import com.app.masterplan.presentation.ui.theme.Gray
import com.app.masterplan.presentation.ui.theme.GreenSoft
import com.app.masterplan.presentation.ui.theme.RedSoft
import com.app.masterplan.presentation.ui.theme.YellowSoft

@Composable
fun RequestsListItemCard(request: AdminRequest, onClick: () -> Unit){

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        ){
            Text(
                text = request.title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.TopStart).padding(10.dp)
            )

            val statusColor = when(request.status){
                AdminRequestStatus.NOT_STARTED -> RedSoft
                AdminRequestStatus.IN_PROGRESS -> YellowSoft
                AdminRequestStatus.COMPLETED -> GreenSoft
                AdminRequestStatus.INVALID -> Gray
            }

            Icon(
                imageVector = Icons.Filled.Circle,
                contentDescription ="StatusIcon",
                tint = statusColor,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 10.dp)
            )

        }

    }

}