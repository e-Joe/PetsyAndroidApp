package com.bytecode.petsy.data.model.dto.brushing

import androidx.compose.ui.graphics.Color
import com.bytecode.petsy.data.model.dto.dog.DogDto
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.composed.ComposedChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.composed.plus
import com.patrykandpatrick.vico.core.entry.entryOf
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale


private const val COLOR_1_CODE = 0xFF63C4AF
private const val COLOR_2_CODE = 0xFFF18989
private val color1 = Color(COLOR_1_CODE)
private val color2 = Color(COLOR_2_CODE)

data class PetsieChartData(
    val chartEntryModelProducer: ComposedChartEntryModelProducer<ChartEntryModel> = generateEmptyChartProducer(),
    val chartColors: List<Color> = listOf(color1, color2),
    val chartMaxColumnColor: Color = Color(0xFFF0F0F0),
    val daysOfWeek: List<String> = listOf("Mon", "Mon", "Mon", "Mon", "Mon", "Mon", "Mop"),
    val bottomAxisValueFormatter: AxisValueFormatter<AxisPosition.Horizontal.Bottom> = AxisValueFormatter { x, _ -> daysOfWeek[x.toInt() % daysOfWeek.size] }
)


data class PetsieDataChartEntry(var day: String, var time: Long)

data class PetsieChartDataRequest(
    val firstDogId: Long = 13,
    val secondDogId: Long = 15,
    val firstDog: DogDto?,
    val secondDog: DogDto?,
    val startTime: ZonedDateTime = ZonedDateTime.now().withHour(0).withMinute(0).withSecond(0),
    val endTime: ZonedDateTime = ZonedDateTime.now().withHour(23).withMinute(59).withSecond(59)
)

fun generateEmptyChartProducer(): ComposedChartEntryModelProducer<ChartEntryModel> {
    val chartEntryModelProducer = ChartEntryModelProducer()

    val multiDataSetChartEntryModelProducer =
        ChartEntryModelProducer()

    val entriesDog1 = mutableListOf<FloatEntry>()
    entriesDog1.add(entryOf(0, 0))
    entriesDog1.add(entryOf(1, 0))
    entriesDog1.add(entryOf(2, 0))
    entriesDog1.add(entryOf(3, 0))
    entriesDog1.add(entryOf(4, 0))
    entriesDog1.add(entryOf(5, 0))
    entriesDog1.add(entryOf(5, 0))

    val entriesDog2 = mutableListOf<FloatEntry>()
    entriesDog2.add(entryOf(0, 0))
    entriesDog2.add(entryOf(1, 0))
    entriesDog2.add(entryOf(2, 0))
    entriesDog2.add(entryOf(3, 0))
    entriesDog2.add(entryOf(4, 0))
    entriesDog2.add(entryOf(5, 0))
    entriesDog2.add(entryOf(5, 0))

    val entriesMaxValue = ArrayList<FloatEntry>()

    val entry1 = entryOf(0, 120)
    val entry2 = entryOf(1, 120)
    val entry3 = entryOf(2, 120)
    val entry4 = entryOf(3, 120)
    val entry5 = entryOf(4, 120)
    val entry6 = entryOf(5, 120)
    val entry7 = entryOf(6, 120)

    entriesMaxValue.add(entry1)
    entriesMaxValue.add(entry2)
    entriesMaxValue.add(entry3)
    entriesMaxValue.add(entry4)
    entriesMaxValue.add(entry5)
    entriesMaxValue.add(entry6)
    entriesMaxValue.add(entry7)

    multiDataSetChartEntryModelProducer.setEntries(
        entries = listOf(entriesDog1, entriesDog2)
    )

    chartEntryModelProducer.setEntries(
        entries = listOf(
            entriesMaxValue,
            entriesMaxValue
        )
    )

    return chartEntryModelProducer + multiDataSetChartEntryModelProducer
}

fun getChartDaysForPeriod(starDate: ZonedDateTime): List<String> {

    var day1 = starDate
    var day2 = starDate.plusDays(1)
    var day3 = starDate.plusDays(2)
    var day4 = starDate.plusDays(3)
    var day5 = starDate.plusDays(4)
    var day6 = starDate.plusDays(5)
    var day7 = starDate.plusDays(6)

    val locale: Locale = Locale.getDefault()
    val formatter = DateTimeFormatter.ofPattern("E", locale)

    val a = listOf<String>(
        day1.format(formatter),
        day2.format(formatter),
        day3.format(formatter),
        day4.format(formatter),
        day5.format(formatter),
        day6.format(formatter),
        day7.format(formatter)
    )

    return a
}