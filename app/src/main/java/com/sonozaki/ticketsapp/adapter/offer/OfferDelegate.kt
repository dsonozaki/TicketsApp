package com.sonozaki.ticketsapp.adapter.offer

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.sonozaki.ticketsapp.R
import com.sonozaki.ticketsapp.databinding.OfferItemBinding
import com.sonozaki.ticketsapp.domain.entities.Offer
import com.sonozaki.ticketsapp.utils.formatDecimal

private const val DIE_ANTWOOD = 1
private const val SOCRAT_AND_LERA = 2
private const val LAMPA = 3


fun getOfferDelegate() = adapterDelegateViewBinding<Offer, Offer, OfferItemBinding>(
    { layoutInflater, root -> OfferItemBinding.inflate(layoutInflater, root, false) }
) {
    bind {
        binding.tvTitle.text = item.title
        binding.tvTown.text = item.town
        binding.tvPrice.text =
            context.getString(R.string.ticket_cost, item.price.value.formatDecimal())
        val imageId = when (item.id) {
            DIE_ANTWOOD -> R.drawable.die_antwood
            SOCRAT_AND_LERA -> R.drawable.socrat_i_lera
            LAMPA -> R.drawable.lampa
            else -> throw Exception("Wrond id")
        }
        binding.shapeableIvOffer.setImageResource(imageId)
    }
}

