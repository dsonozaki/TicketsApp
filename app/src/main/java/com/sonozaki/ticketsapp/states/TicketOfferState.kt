package com.sonozaki.ticketsapp.states

import com.sonozaki.ticketsapp.domain.entities.RequestResult
import com.sonozaki.ticketsapp.domain.entities.TicketOffer

sealed class TicketOfferState {
    object Loading : TicketOfferState()
    data class Data(val data: RequestResult<List<TicketOffer>>) : TicketOfferState()
}