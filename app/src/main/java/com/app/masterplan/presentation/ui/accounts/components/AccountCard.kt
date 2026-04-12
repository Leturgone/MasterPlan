package com.app.masterplan.presentation.ui.accounts.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ModeEditOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.masterplan.R
import com.app.masterplan.domain.model.userManagement.UserRole
import com.app.masterplan.presentation.ui.accounts.viewmodel.AccountCardViewModel
import com.app.masterplan.presentation.ui.common.AvatarNameSec
import com.app.masterplan.presentation.ui.common.MasterPlanState
import com.app.masterplan.presentation.ui.theme.RedSoft
import com.app.masterplan.presentation.ui.theme.YellowSoft

@Composable
fun AccountCard(viewModel: AccountCardViewModel, editUserButtonOnClick: () -> Unit, afterDelete: () -> Unit){

    val employeeData = viewModel.employeeFlow.collectAsState()
    val userDataFlow = viewModel.userDataFlow.collectAsState()

    val directorDataFlow = viewModel.directorFlow.collectAsState()

    Column(
        modifier = Modifier.height(500.dp).padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.Start
    ) {


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            employeeData.value?.let { employee ->
                Text(
                    text = "${employee.name} ${employee.surname} ${employee.patronymic ?: ""}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.width(200.dp),
                    softWrap = true, // перенос слов
                )
            }


            Row(
                modifier = Modifier.width(100.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {
                    editUserButtonOnClick()
                }) {
                    Icon(
                        imageVector = Icons.Default.ModeEditOutline,
                        contentDescription = "editUserButton",
                        modifier = Modifier,
                        tint = YellowSoft
                    )
                }

                IconButton(onClick = {
                    viewModel.deleteUser()
                    afterDelete()
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "deleteUserButton",
                        modifier = Modifier,
                        tint = RedSoft
                    )
                }
            }
        }

        when(userDataFlow.value){
            is MasterPlanState.Failure -> Text(
                stringResource(R.string.error_while_loading)
            )
            MasterPlanState.Loading -> CircularProgressIndicator()
            is MasterPlanState.Success -> {

                val userData = (userDataFlow.value as MasterPlanState.Success).result
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${stringResource(R.string.login_input)}: " ,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.End
                    )

                    Text(
                        text = userData.login,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black,
                        textAlign = TextAlign.End
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${stringResource(R.string.roles)}: " ,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.End
                    )


                    val roles = userData.roles.map {
                        when(it){
                            UserRole.ADMIN -> stringResource(R.string.admin)
                            UserRole.EMPLOYEE -> stringResource(R.string.employee)
                            UserRole.DIRECTOR -> stringResource(R.string.director)
                        }
                    }.joinToString(", ")

                    Text(
                        text = roles,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black,
                        textAlign = TextAlign.End
                    )
                }
            }
            MasterPlanState.Waiting -> null
        }


        when(directorDataFlow.value) {
            is MasterPlanState.Failure -> Text(
                stringResource(R.string.error_while_loading)
            )
            MasterPlanState.Loading -> CircularProgressIndicator()
            is MasterPlanState.Success -> {
                Text(
                    text = "${stringResource(R.string.director)}: " ,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.End
                )

                val director  = (directorDataFlow.value as MasterPlanState.Success).result
                val initials = "${director.surname[0]}.${director.name[0]}"
                val nameWithInitials = "${director.surname} ${director.name[0]}.${director.patronymic?.get(0)?:""}"
                AvatarNameSec(initials,nameWithInitials, Modifier)
            }
            MasterPlanState.Waiting -> null
        }
    }

}