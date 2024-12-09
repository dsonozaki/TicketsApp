package com.sonozaki.ticketsapp.domain.repositories

import com.sonozaki.ticketsapp.domain.entities.Offer
import com.sonozaki.ticketsapp.domain.entities.RequestResult
import kotlinx.coroutines.flow.Flow


interface OfferRepository {
    fun getOffers(): Flow<RequestResult<List<Offer>>>
    suspend fun updateData()
}