package com.sonozaki.ticketsapp.adapter.ticket

import androidx.recyclerview.widget.DiffUtil
import com.sonozaki.ticketsapp.domain.entities.Ticket
import javax.inject.Inject

class TicketItemCallback @Inject constructor() : DiffUtil.ItemCallback<Ticket>() {
    override fun areItemsTheSame(
        oldItem: Ticket,
        newItem: Ticket
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Ticket,
        newItem: Ticket
    ): Boolean {
        return oldItem == newItem
    }
}