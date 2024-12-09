package com.example.data.reporitories

import com.example.data.db.dao.TicketsDAO
import com.example.data.dto.TicketOffersResponseDto
import com.example.data.dto.TicketsResponseDto
import com.example.data.mappers.TicketMapper
import com.example.data.network.TicketsApiService
import com.example.data.network.safeApiCall
import com.sonozaki.ticketsapp.domain.entities.RequestResult
import com.sonozaki.ticketsapp.domain.entities.Ticket
import com.sonozaki.ticketsapp.domain.entities.TicketOffer
import com.sonozaki.ticketsapp.domain.entities.TravelParams
import com.sonozaki.ticketsapp.domain.repositories.TicketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge

class TicketRepositoryImpl(
    private val ticketsApiService: TicketsApiService,
    private val ticketsDAO: TicketsDAO,
    private val ticketMapper: TicketMapper,
    private val errorFlow: MutableSharedFlow<RequestResult.Error<List<Ticket>>>
) : TicketRepository {


    override fun getTickets(): Flow<RequestResult<List<Ticket>>> =
        merge(
            ticketsDAO.getTickets().map { RequestResult.Data(ticketMapper.mapDbToDomainList(it)) },
            errorFlow
        )

    override suspend fun updateTickets(travelParams: TravelParams) {
        val tickets = safeApiCall { ticketsApiService.getTickets() }
        when (tickets) {
            is RequestResult.Error<TicketsResponseDto> -> errorFlow.emit(
                RequestResult.Error<List<Ticket>>(
                    tickets.errorText
                )
            )

            is RequestResult.Data<TicketsResponseDto> -> {
                ticketsDAO.clear()
                ticketsDAO.insert(ticketMapper.mapDtoToDbList(tickets.data))
            }
        }
    }
}