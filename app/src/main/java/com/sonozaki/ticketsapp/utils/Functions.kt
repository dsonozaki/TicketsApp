package com.sonozaki.ticketsapp.utils

import java.text.DecimalFormat

fun Int.formatDecimal(): String {
    val df: DecimalFormat = DecimalFormat("###,###,###")
    return df.format(this).replace(',', ' ')
}