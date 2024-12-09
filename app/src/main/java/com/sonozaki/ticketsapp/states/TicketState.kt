package com.sonozaki.ticketsapp.states

import com.sonozaki.ticketsapp.domain.entities.RequestResult
import com.sonozaki.ticketsapp.domain.entities.Ticket

sealed class TicketState {
    object Loading: TicketState()
    data class Data(val result: RequestResult<List<Ticket>>): TicketState()
}