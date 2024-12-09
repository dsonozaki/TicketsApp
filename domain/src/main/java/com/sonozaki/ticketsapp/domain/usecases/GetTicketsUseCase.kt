package com.sonozaki.ticketsapp.domain.usecases

import com.sonozaki.ticketsapp.domain.entities.RequestResult
import com.sonozaki.ticketsapp.domain.entities.Ticket
import com.sonozaki.ticketsapp.domain.entities.TravelParams
import com.sonozaki.ticketsapp.domain.repositories.CachedParamsRepository
import com.sonozaki.ticketsapp.domain.repositories.TicketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTicketsUseCase @Inject constructor(private val ticketRepository: TicketRepository) {
    operator fun invoke(): Flow<RequestResult<List<Ticket>>> {
        return ticketRepository.getTickets()
    }
}