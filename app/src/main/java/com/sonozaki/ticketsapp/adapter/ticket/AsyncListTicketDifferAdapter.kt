package com.sonozaki.ticketsapp.adapter.ticket

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.sonozaki.ticketsapp.domain.entities.Ticket
import javax.inject.Inject
import javax.inject.Named

class AsyncListTicketDifferAdapter @Inject constructor(
    diffCallback: TicketItemCallback,
    @Named("no badge")
    ticketDelegate: AdapterDelegate<List<Ticket>>,
    @Named("badge")
    ticketWithBadgeDelegate: AdapterDelegate<List<Ticket>>
) : AsyncListDifferDelegationAdapter<Ticket>(diffCallback) {
    init {
        delegatesManager.apply {
            addDelegate(USUAL, ticketDelegate)
            addDelegate(WITH_BADGE, ticketWithBadgeDelegate)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (items[position].badge == null)
            return USUAL
        return WITH_BADGE
    }

    companion object {
        private const val USUAL = 0
        private const val WITH_BADGE = 1
    }
}