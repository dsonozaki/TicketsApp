package com.sonozaki.ticketsapp.domain.repositories

import com.sonozaki.ticketsapp.domain.entities.TravelParams

/**
 * This repository stores the parameters of cached data about ticket offers and tickets
 */
interface CachedParamsRepository {
    /**
     * Update cached ticket offers parameters.
     * @param params - params of travel. Include departure city, arrival city and departure date
     * @return true, if parameters provided differ from old parameters
     */
    suspend fun updateCachedOffersParams(params: TravelParams): Boolean
    /**
     * Update cached ticket parameters.
     * @param params - params of travel. Include departure city, arrival city and departure date
     * @return true, if parameters provided differ from old parameters
     */
    suspend fun updateCachedTicketsParams(params: TravelParams): Boolean
}