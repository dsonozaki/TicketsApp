package com.sonozaki.ticketsapp.domain.entities

sealed class RequestResult<T> {
    class Error<T>(val errorText: String): RequestResult<T>()
    class Data<T>(val data: T): RequestResult<T>()
}