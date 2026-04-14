package com.app.masterplan.presentation.ui.accounts.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.app.masterplan.R
import com.app.masterplan.presentation.ui.common.InputTextField

@Composable
fun AccountDataEditComponent(
    profileData: ProfileData,
    roles: List<RoleData>,
    onLoginChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRoleToggle: (RoleData) -> Unit
){
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {


        InputTextField(
            value = profileData.login,
            label = { Text(stringResource(R.string.login_input)) },
            onValueChange = onLoginChange,
            modifier = Modifier.fillMaxWidth()
        )

        InputTextField(
            value = profileData.password,
            onValueChange = onPasswordChange,
            label = { Text(stringResource(R.string.password)) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions =  KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )


        // Список ролей с переключателями
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(roles.size) {
                val role = roles[it]
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(role.role.name)
                    Switch(
                        checked = role.isSelected,
                        onCheckedChange = { onRoleToggle(role) }
                    )
                }
            }
        }
    }
}