package com.sonozaki.ticketsapp.utils

import android.content.Context
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

fun daysSinceUnixEpoch(): Long {
    val now = Instant.now()
    val unixEpoch = Instant.EPOCH
    return ChronoUnit.DAYS.between(unixEpoch, now)
}

fun timestampToDays(timestamp: Long): Long {
    return TimeUnit.MILLISECONDS.toDays(timestamp)
}

data class DateParts(val date: String, val dayOfWeek: String)

fun formatDaysSinceEpoch(context: Context, daysSinceEpoch: Long): DateParts {
    val millis = daysSinceEpoch * DAY_MULTIPLIER

    val date = Date(millis)

    // It is possible to find the system locale in this way, but it is not clear from the task whether we use only Russian locale or whether it should be changed dynamically
    //val locale = context.resources.configuration.getLocales().get(0)
    val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale(LOCALE))
    val dayOfWeekFormat = SimpleDateFormat(WEEK_DAY_FORMAT, Locale(LOCALE))
    return DateParts(dateFormat.format(date), dayOfWeekFormat.format(date))
}

fun formatDateTimeToHours(isoString: String): String {
    val inputFormat = SimpleDateFormat(DATE_TIME_INPUT_FORMAT, Locale(LOCALE))
    val outputFormat = SimpleDateFormat(TIME_FORMAT, Locale(LOCALE))

    val date = inputFormat.parse(isoString)
    return outputFormat.format(date ?: Date())
}

fun calculateHoursDifference(startIsoString: String, endIsoString: String): String {
    val inputFormat = SimpleDateFormat(DATE_TIME_INPUT_FORMAT, Locale(LOCALE))
    val startDate = inputFormat.parse(startIsoString)
    val endDate = inputFormat.parse(endIsoString)
    val diffInMillis = endDate.time - startDate.time
    val diffInHours = diffInMillis / (HOUR_MULTIPLIER)
    return "%.1f".format(diffInHours)
}

fun daysSinceEpochToDate(daysSinceEpoch: Long): String {
    val millis = daysSinceEpoch * DAY_MULTIPLIER

    val date = Date(millis)

    // Форматируем дату в нужный вид
    val dateFormat = SimpleDateFormat(FULL_DATE_FORMAT, Locale(LOCALE))
    return dateFormat.format(date)
}

private const val DATE_TIME_INPUT_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
private const val TIME_FORMAT = "HH:mm"
private const val DATE_FORMAT = "d MMM"
private const val FULL_DATE_FORMAT = "d MMMM"
private const val WEEK_DAY_FORMAT = "E"
private const val LOCALE = "ru"
private const val DAY_MULTIPLIER = 24 * 60 * 60 * 1000L
private const val HOUR_MULTIPLIER = 1000.0 * 60 * 60