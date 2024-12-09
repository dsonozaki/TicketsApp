package com.sonozaki.ticketsapp.adapter.ticketOffer;

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter;
import com.sonozaki.ticketsapp.domain.entities.TicketOffer
import javax.inject.Inject

class AsyncListTicketOfferDifferAdapter @Inject constructor(diffCallback: TicketOfferItemCallback, ticketOfferDelegate:  AdapterDelegate<List<TicketOffer>>
): AsyncListDifferDelegationAdapter<TicketOffer>(diffCallback) {
    init {
        delegatesManager.addDelegate(ticketOfferDelegate)
    }
}