package com.sonozaki.ticketsapp.adapter.ticketOffer

import androidx.core.content.res.ResourcesCompat
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.sonozaki.ticketsapp.R
import com.sonozaki.ticketsapp.databinding.TicketOfferItemBinding
import com.sonozaki.ticketsapp.domain.entities.TicketOffer
import com.sonozaki.ticketsapp.utils.colorResToColorInt
import com.sonozaki.ticketsapp.utils.formatDecimal

private const val RED = 0
private const val BLUE = 1
private const val WHITE = 2

fun getTicketOfferDelegate() = adapterDelegateViewBinding<TicketOffer, TicketOffer, TicketOfferItemBinding>(
    { layoutInflater, root -> TicketOfferItemBinding.inflate(layoutInflater, root, false) }
) {
    bind {
        binding.price.text = context.getString(R.string.price,item.price.value.formatDecimal())
        binding.companyName.text = item.title
        binding.availableTime.text = item.timeRange
        val color = when (layoutPosition) {
            RED -> R.color.red
            BLUE -> R.color.blue
            WHITE -> R.color.white
            else -> throw Exception("Wrong position")
        }
        binding.itemCircle.background.setTint(colorResToColorInt(color,context))
    }
}

