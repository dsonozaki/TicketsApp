package com.sonozaki.ticketsapp.adapter.ticketOffer

import androidx.recyclerview.widget.DiffUtil
import com.sonozaki.ticketsapp.domain.entities.Offer
import com.sonozaki.ticketsapp.domain.entities.TicketOffer
import javax.inject.Inject

class TicketOfferItemCallback @Inject constructor(): DiffUtil.ItemCallback<TicketOffer>() {
    override fun areItemsTheSame(
        oldItem: TicketOffer,
        newItem: TicketOffer
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: TicketOffer,
        newItem: TicketOffer
    ): Boolean {
        return oldItem == newItem
    }
}