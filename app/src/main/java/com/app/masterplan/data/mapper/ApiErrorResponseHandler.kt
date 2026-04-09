package com.app.masterplan.data.mapper

import android.util.Log
import com.app.masterplan.data.api.exception.ApiErrorResponse
import com.app.masterplan.data.exception.ApiException
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import retrofit2.Response
import java.time.LocalDateTime

object ApiErrorResponseHandler {
    fun  <T> handleResponse(
        response: Response<T>,
        errorMapper: (ApiErrorResponse) -> ApiException
    ): T {
        return if (response.isSuccessful){
            response.body() ?: throw NullPointerException("Response body is null")
        }
        else{
            try {
                val gson = Gson()
                val body = response.errorBody()?.string() ?: throw NullPointerException("Error body is null")
                val errorResponse = gson.fromJson(body, ApiErrorResponse::class.java)
                throw errorMapper(errorResponse)
            }catch (e: JsonSyntaxException){
                Log.e("ApiErrorResponseHandler",e.message.toString())
                throw ApiException.AdminApiException(
                    status = -1,
                    apiMessage = "Failed to parse admin error: ${e.message}"
                )
            }
        }
    }
}