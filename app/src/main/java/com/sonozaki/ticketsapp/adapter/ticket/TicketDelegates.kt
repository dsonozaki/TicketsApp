package com.sonozaki.ticketsapp.adapter.ticket

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.sonozaki.ticketsapp.R
import com.sonozaki.ticketsapp.databinding.TicketItemBinding
import com.sonozaki.ticketsapp.databinding.TicketItemWithBadgeBinding
import com.sonozaki.ticketsapp.domain.entities.Ticket
import com.sonozaki.ticketsapp.utils.calculateHoursDifference
import com.sonozaki.ticketsapp.utils.colorResToColorInt
import com.sonozaki.ticketsapp.utils.formatDateTimeToHours
import com.sonozaki.ticketsapp.utils.formatDecimal

fun getTicketDelegate() = adapterDelegateViewBinding<Ticket, Ticket, TicketItemBinding>(
    { layoutInflater, root -> TicketItemBinding.inflate(layoutInflater, root, false) }
) {
    bind {
        binding.price.text = context.getString(R.string.price,item.price.value.formatDecimal())
        binding.timeStart.text = formatDateTimeToHours(item.departure.date)
        binding.timeEnd.text = formatDateTimeToHours(item.arrival.date)
        binding.origin.text = item.departure.airport
        binding.destination.text = item.arrival.airport
        var conditions = context.getString(R.string.travel_length, calculateHoursDifference(item.departure.date,item.arrival.date))
        if (item.hasTransfer) {
            binding.conditions.text = conditions
        } else {
            val styledText = createStyledText(conditions,"/",context.getString(R.string.has_no_transfer), context)
            binding.conditions.text = styledText
        }
    }
}

fun getTicketDelegateWithBadge() = adapterDelegateViewBinding<Ticket, Ticket, TicketItemWithBadgeBinding>(
    { layoutInflater, root -> TicketItemWithBadgeBinding.inflate(layoutInflater, root, false) }
) {
    bind {
        binding.commentBadge.text = item.badge
        binding.price.text = context.getString(R.string.price,item.price.value.formatDecimal())
        binding.timeStart.text = formatDateTimeToHours(item.departure.date)
        binding.timeEnd.text = formatDateTimeToHours(item.arrival.date)
        binding.origin.text = item.departure.airport
        binding.destination.text = item.arrival.airport
        var conditions = context.getString(R.string.travel_length, calculateHoursDifference(item.departure.date,item.arrival.date))
        if (item.hasTransfer) {
            binding.conditions.text = conditions
        } else {
            val styledText = createStyledText(conditions,DIVIDER,context.getString(R.string.has_no_transfer), context)
            binding.conditions.text = styledText
        }
    }
}

private fun createStyledText(firstPart: String, divider: String, secondPart: String, context: Context): SpannableStringBuilder {
    val spannable = SpannableStringBuilder()

    spannable.append(firstPart)
    spannable.setSpan(
        ForegroundColorSpan(Color.WHITE),
        0,
        firstPart.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    val dividerStart = spannable.length
    spannable.append(" ")
    spannable.append(divider)
    spannable.setSpan(
        ForegroundColorSpan(colorResToColorInt(R.color.grey6,context)),
        dividerStart,
        spannable.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    val secondPartStart = spannable.length
    spannable.append(" ")
    spannable.append(secondPart)
    spannable.setSpan(
        ForegroundColorSpan(Color.WHITE),
        secondPartStart,
        spannable.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    return spannable
}

private const val DIVIDER = "/"
