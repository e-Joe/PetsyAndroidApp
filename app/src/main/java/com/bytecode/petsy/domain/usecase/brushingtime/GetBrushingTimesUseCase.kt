package com.bytecode.petsy.domain.usecase.brushingtime

import android.util.Log
import androidx.annotation.VisibleForTesting
import com.bytecode.framework.usecase.LocalUseCase
import com.bytecode.petsy.data.model.dto.brushing.BrushingTimeDto
import com.bytecode.petsy.data.model.dto.brushing.PetsieChartDataRequest
import com.bytecode.petsy.data.model.dto.brushing.PetsieDataChartEntry
import com.bytecode.petsy.data.repository.brushingtime.BrushingTimeRepository
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.composed.ComposedChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.composed.plus
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class GetBrushingTimesUseCase @Inject constructor(
    @get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    internal val repository: BrushingTimeRepository
) : LocalUseCase<PetsieChartDataRequest, Pair<List<BrushingTimeDto>, List<BrushingTimeDto>>>() {


    override suspend fun FlowCollector<Pair<List<BrushingTimeDto>, List<BrushingTimeDto>>>.execute(
        request: PetsieChartDataRequest
    ) {
        val brushingTimes = repository.getAllTimes()

        val firstDogTimes =
            brushingTimes.filter { it.dogId == request.firstDogId }
                .sortedBy { it.startTime }
                .filter { it.startTime.isAfter(request.startTime) }
                .filter { it.startTime.isBefore(request.endTime) }
        val firstDogTimesByDays = firstDogTimes.groupBy { it.startTime.toLocalDate() }

        val secondDogTimes =
            brushingTimes.filter { it.dogId == request.secondDogId }
                .sortedBy { it.startTime }
                .filter { it.startTime.isAfter(request.startTime) }
                .filter { it.startTime.isBefore(request.endTime) }
        val secondDogTimesByDays = secondDogTimes.groupBy { it.startTime.toLocalDate() }

        var tempFirstDogChartData = arrayListOf<PetsieDataChartEntry>()
        firstDogTimesByDays.entries.forEach {
            var date = it.key
            var time: Long = 0

            it.value.forEach {
                time += it.duration
            }

            tempFirstDogChartData.add(PetsieDataChartEntry(date.dayOfWeek.name, time))
        }

        var tempSecondDogChartData = arrayListOf<PetsieDataChartEntry>()
        secondDogTimesByDays.entries.forEach {
            var date = it.key
            var time: Long = 0

            it.value.forEach {
                time += it.duration
            }

            tempSecondDogChartData.add(PetsieDataChartEntry(date.dayOfWeek.name, time))
        }

        var day1 = request.startTime.toLocalDate().dayOfWeek
        var day2 = request.startTime.plusDays(1).toLocalDate().dayOfWeek
        var day3 = request.startTime.plusDays(2).toLocalDate().dayOfWeek
        var day4 = request.startTime.plusDays(3).toLocalDate().dayOfWeek
        var day5 = request.startTime.plusDays(4).toLocalDate().dayOfWeek
        var day6 = request.startTime.plusDays(5).toLocalDate().dayOfWeek
        var day7 = request.startTime.plusDays(6).toLocalDate().dayOfWeek

        var firsDayDataDog1 = tempFirstDogChartData.find { it.day == day1.name }
        var secondDayDataDog1 = tempFirstDogChartData.find { it.day == day2.name }
        var thirdDayDataDog1 = tempFirstDogChartData.find { it.day == day3.name }
        var fourthDayDataDog1 = tempFirstDogChartData.find { it.day == day4.name }
        var fiftDayDataDog1 = tempFirstDogChartData.find { it.day == day5.name }
        var sixtDayDataDog1 = tempFirstDogChartData.find { it.day == day6.name }
        var seventDayDataDog1 = tempFirstDogChartData.find { it.day == day7.name }

        var firstDogChartData = arrayListOf<PetsieDataChartEntry>()

        if (firsDayDataDog1 == null)
            firstDogChartData.add(PetsieDataChartEntry(day1.name, 0))
        else
            firstDogChartData.add(firsDayDataDog1)

        if (secondDayDataDog1 == null)
            firstDogChartData.add(PetsieDataChartEntry(day2.name, 0))
        else
            firstDogChartData.add(secondDayDataDog1)

        if (thirdDayDataDog1 == null)
            firstDogChartData.add(PetsieDataChartEntry(day3.name, 0))
        else
            firstDogChartData.add(thirdDayDataDog1)

        if (fourthDayDataDog1 == null)
            firstDogChartData.add(PetsieDataChartEntry(day4.name, 0))
        else
            firstDogChartData.add(fourthDayDataDog1)

        if (fiftDayDataDog1 == null)
            firstDogChartData.add(PetsieDataChartEntry(day5.name, 0))
        else
            firstDogChartData.add(fiftDayDataDog1)

        if (sixtDayDataDog1 == null)
            firstDogChartData.add(PetsieDataChartEntry(day6.name, 0))
        else
            firstDogChartData.add(sixtDayDataDog1)

        if (seventDayDataDog1 == null)
            firstDogChartData.add(PetsieDataChartEntry(day7.name, 0))
        else
            firstDogChartData.add(seventDayDataDog1)


        var firsDayDataDog2 = tempSecondDogChartData.find { it.day == day1.name }
        var secondDayDataDog2 = tempSecondDogChartData.find { it.day == day2.name }
        var thirdDayDataDog2 = tempSecondDogChartData.find { it.day == day3.name }
        var fourthDayDataDog2 = tempSecondDogChartData.find { it.day == day4.name }
        var fiftDayDataDog2 = tempSecondDogChartData.find { it.day == day5.name }
        var sixtDayDataDog2 = tempSecondDogChartData.find { it.day == day6.name }
        var seventDayDataDog2 = tempSecondDogChartData.find { it.day == day7.name }

        var secondDogChartData = arrayListOf<PetsieDataChartEntry>()

        if (firsDayDataDog2 == null)
            secondDogChartData.add(PetsieDataChartEntry(day1.name, 0))
        else
            secondDogChartData.add(firsDayDataDog2)

        if (secondDayDataDog2 == null)
            secondDogChartData.add(PetsieDataChartEntry(day2.name, 0))
        else
            secondDogChartData.add(secondDayDataDog2)

        if (thirdDayDataDog2 == null)
            secondDogChartData.add(PetsieDataChartEntry(day3.name, 0))
        else
            secondDogChartData.add(thirdDayDataDog2)

        if (fourthDayDataDog2 == null)
            secondDogChartData.add(PetsieDataChartEntry(day4.name, 0))
        else
            secondDogChartData.add(fourthDayDataDog2)

        if (fiftDayDataDog2 == null)
            secondDogChartData.add(PetsieDataChartEntry(day5.name, 0))
        else
            secondDogChartData.add(fiftDayDataDog2)

        if (sixtDayDataDog2 == null)
            secondDogChartData.add(PetsieDataChartEntry(day6.name, 0))
        else
            secondDogChartData.add(sixtDayDataDog2)

        if (seventDayDataDog2 == null)
            secondDogChartData.add(PetsieDataChartEntry(day7.name, 0))
        else
            secondDogChartData.add(seventDayDataDog2)


        Log.d("Times", "day1: $day1")
        Log.d("Times", "day2: $day2")
        Log.d("Times", "day3: $day3")
        Log.d("Times", "day4: $day4")
        Log.d("Times", "day5: $day5")
        Log.d("Times", "day6: $day6")
        Log.d("Times", "day7: $day7")
        Log.d("Times", "end date: ${request.endTime.toLocalDate().dayOfWeek}")


        Log.d("Times", "First dog days: " + firstDogChartData.size.toString())
        Log.d("Times", "Second dog days: " + secondDogChartData.size.toString())

        val chartEntryModelProducer: ChartEntryModelProducer = ChartEntryModelProducer()

        val multiDataSetChartEntryModelProducer: ChartEntryModelProducer =
            ChartEntryModelProducer()

        val composedChartEntryModelProducer: ComposedChartEntryModelProducer<ChartEntryModel> =
            chartEntryModelProducer + multiDataSetChartEntryModelProducer


//        emit(dogs.toDogDtoList())
    }
}