package com.app.masterplan.data.mapper

import com.app.masterplan.data.api.authApi.dto.responce.LoginResponse
import com.app.masterplan.domain.model.auth.JwtToken
import com.app.masterplan.domain.model.userManagement.UserRole

object AuthResponseMapper {
    fun toDomain(resp: LoginResponse): JwtToken{
        val roles = resp.roles.mapNotNull {
            try {
                UserRole.valueOf(it)
            }catch (_: Exception){
                null
            }
        }.toSet()
        return JwtToken(
            token = resp.token,
            roles = roles
        )
    }
}