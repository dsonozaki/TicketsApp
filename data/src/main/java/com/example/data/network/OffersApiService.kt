package com.example.data.network

import com.example.data.dto.OffersResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface OffersApiService {

    @GET(OFFERS_URL)
    suspend fun getOffers(): Response<OffersResponseDto>

    companion object {
        private const val OFFERS_URL =
            "/u/0/uc?id=1o1nX3uFISrG1gR-jr_03Qlu4_KEZWhav&export=download"
    }

}