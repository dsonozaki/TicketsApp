package com.example.data.reporitories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.data.dto.TravelParamsDto
import com.example.data.mappers.TravelParamsMapper
import com.example.data.serializers.TravelParamsSerializer
import com.sonozaki.ticketsapp.domain.entities.TravelParams
import com.sonozaki.ticketsapp.domain.repositories.CachedParamsRepository
import kotlinx.coroutines.flow.first

class CachedParamsRepositoryImpl(
    private val context: Context,
    travelParamsSerializer: TravelParamsSerializer,
    private val travelParamsMapper: TravelParamsMapper
) : CachedParamsRepository {

    private val Context.cachedTicketParams by dataStore(
        CACHED_TICKET_PARAMS,
        travelParamsSerializer
    )

    private val Context.cachedTicketOffersParams by dataStore(
        CACHED_TICKET_OFFERS_PARAMS,
        travelParamsSerializer
    )

    private suspend fun checkParams(
        paramsDatastore: DataStore<TravelParamsDto>,
        params: TravelParams
    ): Boolean {
        val currentParams = travelParamsMapper.mapDtoToDomain(paramsDatastore.data.first())
        return currentParams != params
    }

    private suspend fun updateParams(
        paramsDatastore: DataStore<TravelParamsDto>,
        params: TravelParams
    ) {
        paramsDatastore.updateData {
            travelParamsMapper.mapDomainToDto(params)
        }
    }

    override suspend fun updateCachedOffersParams(params: TravelParams) {
        updateParams(context.cachedTicketOffersParams, params)
    }

    override suspend fun checkCachedOffersParams(params: TravelParams): Boolean {
        return checkParams(context.cachedTicketOffersParams, params)
    }

    override suspend fun updateCachedTicketsParams(params: TravelParams) {
        updateParams(context.cachedTicketParams, params)
    }

    override suspend fun checkCachedTicketsParams(params: TravelParams): Boolean {
        return checkParams(context.cachedTicketParams, params)
    }

    companion object {
        private const val CACHED_TICKET_PARAMS = "cached_ticket_params.json"
        private const val CACHED_TICKET_OFFERS_PARAMS = "cached_ticket_offers_params.json"
    }
}