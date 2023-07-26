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

fun ZonedDateTime.formatDateTimeShortMonth(lang: String): String {
    var language = lang

    if(lang == "GB")
        language = "EN"
    val formatter = DateTimeFormatter.ofPattern("dd MMM HH:mm", Locale(language))
    return this.format(formatter)
}

fun ZonedDateTime.formatDateDayMonth(): String {
    val formatter = DateTimeFormatter.ofPattern("dd.MM", Locale.getDefault())
    return this.format(formatter)
}