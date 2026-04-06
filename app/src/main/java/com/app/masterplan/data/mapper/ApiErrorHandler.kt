package com.app.masterplan.data.mapper

import com.app.masterplan.data.api.exception.ApiErrorResponse
import com.app.masterplan.data.exception.ApiException
import com.google.gson.Gson
import retrofit2.Response
import java.time.LocalDateTime

object ApiErrorHandler {
    fun  <T> handleResponse(
        response: Response<T>,
        errorMapper: (ApiErrorResponse) -> ApiException
    ): T {
        val body = response.body() ?: throw NullPointerException("Response body is null")
        return if (response.isSuccessful){
            body
        }
        else{
            try {
                val gson = Gson()
                val errorResponse = gson.fromJson(body.toString(), ApiErrorResponse::class.java)
                throw errorMapper(errorResponse)
            }catch (e: Exception){
                throw ApiException.AdminApiException(
                    status = -1,
                    apiMessage = "Failed to parse admin error: ${e.message}",
                    timestamp = LocalDateTime.now()
                )
            }
        }
    }
}