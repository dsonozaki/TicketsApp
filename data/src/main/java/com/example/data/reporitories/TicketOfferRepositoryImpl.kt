package com.example.data.reporitories

import com.example.data.db.dao.TicketOffersDAO
import com.example.data.dto.TicketOffersResponseDto
import com.example.data.mappers.TicketOfferMapper
import com.example.data.network.OfferTicketsApiService
import com.example.data.network.safeApiCall
import com.sonozaki.ticketsapp.domain.entities.RequestResult
import com.sonozaki.ticketsapp.domain.entities.TicketOffer
import com.sonozaki.ticketsapp.domain.repositories.TicketOfferRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge

class TicketOfferRepositoryImpl(
    private val ticketsOfferApiService: OfferTicketsApiService,
    private val ticketsOfferDAO: TicketOffersDAO,
    private val ticketOfferMapper: TicketOfferMapper,
    //flow of api request errors
    private val errorFlow: MutableSharedFlow<RequestResult.Error<List<TicketOffer>>>
) : TicketOfferRepository {

    override fun getTicketOffers(): Flow<RequestResult<List<TicketOffer>>> =
        //return merged flows of data from database and data from error flow
        merge(
        ticketsOfferDAO.getTicketOffers().filter { it.isNotEmpty() }
            .map { RequestResult.Data(ticketOfferMapper.mapDbToDomainList(it)) }, errorFlow
    )


    override suspend fun updateTicketOffers(): Boolean {
        val ticketOffers = safeApiCall { ticketsOfferApiService.getOfferTickets() }
        when (ticketOffers) {
            is RequestResult.Error<TicketOffersResponseDto> -> {
                errorFlow.emit(
                    RequestResult.Error<List<TicketOffer>>(
                        ticketOffers.errorText
                    )
                )
                return false
            }

            is RequestResult.Data<TicketOffersResponseDto> -> {
                ticketsOfferDAO.clear()
                ticketsOfferDAO.insert(ticketOfferMapper.mapDtoToDbList(ticketOffers.data))
                return true
            }
        }
    }
}