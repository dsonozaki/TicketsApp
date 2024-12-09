package com.sonozaki.ticketsapp.states

import com.sonozaki.ticketsapp.domain.entities.Offer
import com.sonozaki.ticketsapp.domain.entities.RequestResult

sealed class OfferState {
    object Loading : OfferState()
    data class Data(val offersResult: RequestResult<List<Offer>>) : OfferState()
}