package com.example.data.network

import com.sonozaki.ticketsapp.domain.entities.RequestResult
import retrofit2.Response
import java.io.IOException

/**
 * Function for getting RequestResult from API calls
 */
suspend fun <T> safeApiCall(unsafeCall: suspend () -> Response<T>): RequestResult<T> {
    return try {
        val response = unsafeCall()
        if (response.isSuccessful) {
            val body = response.body()
            return if (body == null) {
                RequestResult.Error(EMPTY_RESPONSE)
            } else {
                RequestResult.Data(body)
            }
        }
        return RequestResult.Error(SERVER_ERROR)
    } catch (e: IOException) {
        RequestResult.Error<T>("$CONNECTION_ERROR: ${e.message}")
    } catch (e: Exception) {
        RequestResult.Error<T>(e.message ?: "")
    }
}

private const val EMPTY_RESPONSE = "Empty response"
private const val SERVER_ERROR = "Server error"
private const val CONNECTION_ERROR = "Connection error"