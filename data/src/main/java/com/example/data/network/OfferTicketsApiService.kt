package com.example.data.network

import com.example.data.dto.TicketOffersResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface OfferTicketsApiService {

    @GET(OFFER_TICKETS_URL)
    suspend fun getOfferTickets(): Response<TicketOffersResponseDto>

    companion object {
        private const val OFFER_TICKETS_URL =
            "/u/0/uc?id=13WhZ5ahHBwMiHRXxWPq-bYlRVRwAujta&export=download"
    }

}