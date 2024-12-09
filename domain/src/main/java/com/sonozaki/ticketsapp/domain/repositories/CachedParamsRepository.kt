package com.sonozaki.ticketsapp.domain.repositories

import com.sonozaki.ticketsapp.domain.entities.TravelParams

interface CachedParamsRepository {
    suspend fun updateCachedOffersParams(params: TravelParams): Boolean
    suspend fun updateCachedTicketsParams(params: TravelParams): Boolean
}