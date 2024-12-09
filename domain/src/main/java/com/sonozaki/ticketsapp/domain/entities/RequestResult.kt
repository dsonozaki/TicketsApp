package com.sonozaki.ticketsapp.domain.entities

/**
 * Class for storing result of API request. Stores error text or received data
 */
sealed class RequestResult<T> {
    class Error<T>(val errorText: String): RequestResult<T>()
    class Data<T>(val data: T): RequestResult<T>()
}