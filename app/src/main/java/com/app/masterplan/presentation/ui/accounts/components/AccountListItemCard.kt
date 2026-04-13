package com.app.masterplan.presentation.ui.accounts.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.masterplan.domain.model.employee.Employee
import com.app.masterplan.presentation.ui.common.AvatarNameSec

@Composable
fun AccountListItemCard(account: Employee, onClick: () -> Unit){
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

            val initials = "${account.surname[0]}${account.name[0]}"
            val nameWithInitials = "${account.surname} ${account.name[0]}.${account.patronymic?.get(0)?:""}"
            AvatarNameSec(
                initials, nameWithInitials,Modifier.padding(top = 10.dp)
            )
        }

    }

}