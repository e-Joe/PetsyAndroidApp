package com.bytecode.petsy.data.model.dto.brushing

import androidx.compose.ui.graphics.Color
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import com.patrykandpatrick.vico.core.entry.composed.ComposedChartEntryModelProducer

import java.time.ZonedDateTime

data class PetsieChartData(
    val chartEntryModelProducer: ComposedChartEntryModelProducer<ChartEntryModel>,
    val chartColors: List<Color>,
    val chartMaxColumnColor: Color,
    val daysXAxis: List<String>,

    )


data class PetsieDataChartEntry(var day: String, var time: Long)

data class PetsieChartDataRequest(
    val firstDogId: Long = 13,
    val secondDogId: Long = 15,
    val startTime: ZonedDateTime = ZonedDateTime.now().withHour(0).withMinute(0).withSecond(0),
    val endTime: ZonedDateTime = ZonedDateTime.now().withHour(23).withMinute(59).withSecond(59)
)