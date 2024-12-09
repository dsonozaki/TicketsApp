package com.sonozaki.ticketsapp.domain.repositories

import com.sonozaki.ticketsapp.domain.entities.TravelParams

/**
 * This repository stores the parameters of cached data about ticket offers and tickets
 */
interface CachedParamsRepository {
    /**
     * Update cached ticket offers parameters. Must be called only if data updated successfully.
     * @param params - params of travel. Include departure city, arrival city and departure date
     */
    suspend fun updateCachedOffersParams(params: TravelParams)

    /**
     * Check if new ticket offer parameters are different from cached ones.
     * @return true, if parameters provided differ from old parameters
     */
    suspend fun checkCachedOffersParams(params: TravelParams): Boolean

    /**
     * Update cached ticket parameters. Must be called only if data updated successfully.
     * @param params - params of travel. Include departure city, arrival city and departure date
     */
    suspend fun updateCachedTicketsParams(params: TravelParams)

    /**
     * Check if new ticket parameters are different from cached ones.
     * @return true, if parameters provided differ from old parameters
     */
    suspend fun checkCachedTicketsParams(params: TravelParams): Boolean

}