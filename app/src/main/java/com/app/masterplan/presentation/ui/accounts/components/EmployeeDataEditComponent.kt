package com.app.masterplan.presentation.ui.accounts.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.app.masterplan.R
import com.app.masterplan.domain.model.employee.Employee
import com.app.masterplan.presentation.ui.common.CardButton
import com.app.masterplan.presentation.ui.common.InputTextField

@Composable
fun EmployeeDataEditComponent(
    profileData: ProfileData,
    onNameChange: (String) -> Unit,
    onSurnameChange: (String) -> Unit,
    onPatronymicChange: (String) -> Unit,
    onOpenDirectorListClick: () -> Unit,
    directorData: Employee?
){
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {


        InputTextField(
            value = profileData.name,
            onValueChange = onNameChange,
            label = { Text(stringResource(R.string.name)) },
            modifier = Modifier.fillMaxWidth()
        )

        InputTextField(
            value = profileData.surname,
            onValueChange = onSurnameChange,
            label = { Text(stringResource(R.string.surname)) },
            modifier = Modifier.fillMaxWidth()
        )

        InputTextField(
            value = profileData.patronymic?:"",
            onValueChange = onPatronymicChange,
            label ={ Text(stringResource(R.string.patronymic)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        if (directorData == null){
            CardButton(stringResource(R.string.assign_director)) {
                onOpenDirectorListClick()
            }
        }else{
            AccountListItemCard(directorData){
                onOpenDirectorListClick()
            }
        }


    }
}