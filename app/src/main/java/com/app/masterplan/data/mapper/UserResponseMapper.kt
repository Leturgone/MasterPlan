package com.app.masterplan.data.mapper

import com.app.masterplan.data.api.userManagementApi.dto.responce.UserDataResponse
import com.app.masterplan.data.api.userManagementApi.dto.responce.UserUidResponse
import com.app.masterplan.domain.model.userManagement.User
import com.app.masterplan.domain.model.userManagement.UserRole

object UserResponseMapper {

    fun toDomain(response: UserUidResponse) = response.uid

    fun toDomain(response: UserDataResponse): User {
        val roles = response.roles.mapNotNull {
            try {
                UserRole.valueOf(it)
            }catch (_: Exception){
                null
            }
        }.toSet()

        return User(
            id = response.id,
            login = response.login,
            password = response.password,
            roles = roles
        )
    }
}