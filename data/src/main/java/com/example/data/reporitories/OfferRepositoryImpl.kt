package com.example.data.reporitories

import com.example.data.db.dao.OffersDAO
import com.example.data.db.dao.TicketsDAO
import com.example.data.dto.OffersResponseDto
import com.example.data.mappers.OfferMapper
import com.example.data.mappers.TicketMapper
import com.example.data.network.OffersApiService
import com.example.data.network.TicketsApiService
import com.example.data.network.safeApiCall
import com.sonozaki.ticketsapp.domain.entities.Offer
import com.sonozaki.ticketsapp.domain.entities.RequestResult
import com.sonozaki.ticketsapp.domain.entities.Ticket
import com.sonozaki.ticketsapp.domain.entities.TicketOffer
import com.sonozaki.ticketsapp.domain.entities.TravelParams
import com.sonozaki.ticketsapp.domain.repositories.OfferRepository
import com.sonozaki.ticketsapp.domain.repositories.TicketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge

class OfferRepositoryImpl(
    private val offersApiService: OffersApiService,
    private val offersDAO: OffersDAO,
    private val offerMapper: OfferMapper,
    private val errorFlow: MutableSharedFlow<RequestResult.Error<List<Offer>>>) : OfferRepository {
    private var loaded: Boolean = false


    override suspend fun updateData() {
        if (!loaded) {
            val offers = safeApiCall { offersApiService.getOffers() }
            when (offers) {
                is RequestResult.Data<OffersResponseDto> -> {
                    offersDAO.clear()
                    offersDAO.insert(offerMapper.mapDtoToDbList(offers.data))
                    loaded = true
                }

                is RequestResult.Error<OffersResponseDto> -> errorFlow.emit(
                    RequestResult.Error(
                        offers.errorText
                    )
                )
            }
        }
    }

    override fun getOffers(): Flow<RequestResult<List<Offer>>> {
        return merge(
            offersDAO.getOffers().filter { it.isNotEmpty() }
                .map { RequestResult.Data(offerMapper.mapDbToDomainList(it)) },
            errorFlow
        )
    }
}