package com.sonozaki.ticketsapp.adapter.offer

import androidx.recyclerview.widget.DiffUtil
import com.sonozaki.ticketsapp.domain.entities.Offer
import javax.inject.Inject

class OfferItemCallback @Inject constructor() : DiffUtil.ItemCallback<Offer>() {
    override fun areItemsTheSame(
        oldItem: Offer,
        newItem: Offer
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Offer,
        newItem: Offer
    ): Boolean {
        return oldItem == newItem
    }
}