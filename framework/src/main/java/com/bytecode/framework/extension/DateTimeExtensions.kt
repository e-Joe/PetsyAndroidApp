package com.bytecode.framework.extension

import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun ZonedDateTime.isToday(): Boolean {
    val currentDate = LocalDate.now()
    val zonedDateTimeDate = this.toLocalDate()
    return currentDate == zonedDateTimeDate
}

fun ZonedDateTime.formatDateTimeShortMonth(): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMM HH:mm", Locale.getDefault())
    return this.format(formatter)
}

fun ZonedDateTime.formatDateDayMonth(): String {
    val formatter = DateTimeFormatter.ofPattern("dd.MM", Locale.getDefault())
    return this.format(formatter)
}