package com.example.data.network

import com.example.data.dto.OffersResponseDto
import com.example.data.dto.TicketOffersResponseDto
import com.example.data.dto.TicketsResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface TicketsApiService {

    @GET(TICKERS_URL)
    suspend fun getTickets(): Response<TicketsResponseDto>

    companion object {
        private const val TICKERS_URL = "/u/0/uc?export=download&id=1HogOsz4hWkRwco4kud3isZHFQLUAwNBA"
    }

}