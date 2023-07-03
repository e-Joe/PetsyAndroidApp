package com.bytecode.petsy.presentation.ui.screens.mainflow

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.bytecode.framework.base.MvvmViewModel
import com.bytecode.framework.extension.formatDateDayMonth
import com.bytecode.petsy.data.model.dto.brushing.BrushingTimeDto
import com.bytecode.petsy.data.model.dto.color.ColorDto
import com.bytecode.petsy.data.model.dto.dog.DogDto
import com.bytecode.petsy.data.model.dto.user.UserDto
import com.bytecode.petsy.domain.usecase.brushingtime.DeleteTimeForDogUseCase
import com.bytecode.petsy.domain.usecase.brushingtime.SaveBrushingTimeUseCase
import com.bytecode.petsy.domain.usecase.color.GetColorForNewDogUseCase
import com.bytecode.petsy.domain.usecase.color.SaveColorUseCase
import com.bytecode.petsy.domain.usecase.dog.DeleteDogUseCase
import com.bytecode.petsy.domain.usecase.dog.GetDogsUseCase
import com.bytecode.petsy.domain.usecase.dog.SaveDogUseCase
import com.bytecode.petsy.domain.usecase.dog.UpdateDogUseCase
import com.bytecode.petsy.domain.usecase.user.GetLoggedInUserUseCase
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.composed.ComposedChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.composed.plus
import com.patrykandpatrick.vico.core.entry.entryOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class MainFlowViewModel @Inject constructor(
    private val getLoggedInUserUseCase: GetLoggedInUserUseCase,
    private val getDogsUseCase: GetDogsUseCase,
    private val saveBrushingTimeUseCase: SaveBrushingTimeUseCase,
    private val getColorForNewDogUseCase: GetColorForNewDogUseCase,
    private val saveDogUseCase: SaveDogUseCase,
    private val saveColorUseCase: SaveColorUseCase,
    private val updateDogUseCase: UpdateDogUseCase,
    private val deleteDogUseCase: DeleteDogUseCase,
    private val deleteTimeForDogUseCase: DeleteTimeForDogUseCase
) : MvvmViewModel() {

    var state by mutableStateOf(MainFlowState())
    private lateinit var newColor: String
    private lateinit var newDogName: String
    private var insertedDogId: Long = -1

    private var user: UserDto = UserDto()
    private val _userFlow = MutableStateFlow(user)
    val userFLow: StateFlow<UserDto> get() = _userFlow.asStateFlow()

    private var brushingJob: Job? = null
    private val _brushingTime = MutableStateFlow(0)
    val brushingTime = _brushingTime.asStateFlow()

    private val _finishedBrushingTime = MutableStateFlow(0)
    val finishedBrushingTime = _finishedBrushingTime.asStateFlow()

    private var dogs = mutableStateListOf<DogDto>()
    private val _dogsFlow = MutableStateFlow(dogs)
    val dogsFlow: StateFlow<List<DogDto>> get() = _dogsFlow

    private lateinit var startBrushingDateTime: ZonedDateTime

    private var firstChartDog: DogDto? = null
    private var secondChartDog: DogDto? = null

    private var endDate = ZonedDateTime.now().withHour(23).withMinute(59).withSecond(59)
    private var startDate = ZonedDateTime.now().minusDays(7).withHour(0).withMinute(0).withSecond(0)

    private var formattedChartPeriod: String = ""
    private val _formattedChartPeriodFLow = MutableStateFlow(formattedChartPeriod)
    val formattedChartPeriodFlow: StateFlow<String> get() = _formattedChartPeriodFLow

    var deleteDog: DogDto = DogDto()


    internal val chartEntryModelProducer: ChartEntryModelProducer = ChartEntryModelProducer()

    internal val customStepChartEntryModelProducer: ChartEntryModelProducer =
        ChartEntryModelProducer()

    internal val multiDataSetChartEntryModelProducer: ChartEntryModelProducer =
        ChartEntryModelProducer()

    internal val composedChartEntryModelProducer: ComposedChartEntryModelProducer<ChartEntryModel> =
        chartEntryModelProducer + multiDataSetChartEntryModelProducer


    fun generateRandomEntries2(): List<FloatEntry> {
        val result = ArrayList<FloatEntry>()

        val entry1 = entryOf(0, 150)
        val entry2 = entryOf(1, 120)
        val entry3 = entryOf(2, 85)
        val entry4 = entryOf(3, 30)
        val entry5 = entryOf(4, 50)
        val entry6 = entryOf(5, 70)
        val entry7 = entryOf(6, 90)

        result.add(entry1)
        result.add(entry2)
        result.add(entry3)
        result.add(entry4)
        result.add(entry5)
        result.add(entry6)
        result.add(entry7)

        return result
    }

    fun generateRandomEntries3(): List<FloatEntry> {
        val result = ArrayList<FloatEntry>()

        val entry1 = entryOf(0, 100)
        val entry2 = entryOf(1, 90)
        val entry3 = entryOf(2, 80)
        val entry4 = entryOf(3, 70)
        val entry5 = entryOf(4, 60)
        val entry6 = entryOf(5, 50)
        val entry7 = entryOf(6, 40)

        result.add(entry1)
        result.add(entry2)
        result.add(entry3)
        result.add(entry4)
        result.add(entry5)
        result.add(entry6)
        result.add(entry7)

        return result
    }

    fun generateRandomEntries4(): List<FloatEntry> {
        val result = ArrayList<FloatEntry>()

        val entry1 = entryOf(0, 120)
        val entry2 = entryOf(1, 120)
        val entry3 = entryOf(2, 120)
        val entry4 = entryOf(3, 120)
        val entry5 = entryOf(4, 120)
        val entry6 = entryOf(5, 120)
        val entry7 = entryOf(6, 120)

        result.add(entry1)
        result.add(entry2)
        result.add(entry3)
        result.add(entry4)
        result.add(entry5)
        result.add(entry6)
        result.add(entry7)

        return result
    }

    init {
        getLoggedInUser()
        updateChardPeriod()

        viewModelScope.launch {
            while (currentCoroutineContext().isActive) {
                multiDataSetChartEntryModelProducer.setEntries(
                    entries = listOf(generateRandomEntries2(), generateRandomEntries3())
                )
                chartEntryModelProducer.setEntries(
                    entries = listOf(
                        generateRandomEntries4(),
                        generateRandomEntries4()
                    )
                )
                delay(UPDATE_FREQUENCY)
            }
        }
    }

    fun onEvent(event: MainFlowEvent) {

        when (event) {
            is MainFlowEvent.BrushingStateEvent -> {
                state = state.copy(brushingPhase = event.brushingState)

                when (event.brushingState) {
                    BrushingState.NOT_STARTED -> {
                        _finishedBrushingTime.value = 0
                        stopBrushing()
                    }

                    BrushingState.IN_PROGRESS -> {
                        startBrushingDateTime = ZonedDateTime.now()
                        startBrushing()
                    }

                    BrushingState.CONTINUE -> {
                        startBrushing()
                    }

                    BrushingState.PAUSED -> {
                        pauseBrushing()
                    }

                    BrushingState.SAVING -> {
                        pauseBrushing()
                    }

                    BrushingState.SHARING -> {
                        _finishedBrushingTime.value = _brushingTime.value
                        stopBrushing()
                    }
                }
            }

            is MainFlowEvent.DogClickedEvent -> {
                val updatedList = dogs.map {
                    if (event.dogId == it.id) {
                        it.copy(isSelected = !it.isSelected)
                    } else
                        it.copy(isSelected = false)
                }
                dogs.clear()
                dogs.addAll(updatedList)

                val dog = dogs.find { it.id == event.dogId }

                state = state.copy(
                    isDogSelected = dog?.isSelected ?: false
                )
            }

            is MainFlowEvent.DeleteDogClickEvent -> {
                deleteDog = event.dog
            }

            is MainFlowEvent.DeleteDogConfirmedEvent -> {
                deleteDog()
            }

            is MainFlowEvent.ChartDogClickedEvent -> {

                val dogToSelect = dogs.find { it.id == event.dogId }

                if (dogToSelect != null) {
                    val selectedDogs = dogs.filter { it.isSelectedForChart }

                    if (selectedDogs.isEmpty()) {
                        dogToSelect.isSelectedForChart = true
                    } else if (dogToSelect.isSelectedForChart) {
                        dogToSelect.isSelectedForChart = false
                    } else if (selectedDogs.size == 2) {
                        selectedDogs[1].isSelectedForChart = false
                        dogToSelect.isSelectedForChart = true
                    } else {
                        dogToSelect.isSelectedForChart = true
                    }
                }

                var tempDogs = dogs.map { it }
                var counterOfSelectedDogs = tempDogs.count { it.isSelectedForChart }

                if (counterOfSelectedDogs == 0) {
                    tempDogs[0].isSelectedForChart = true
                }

                val selectedDogs = tempDogs.filter { it.isSelectedForChart }

                if (selectedDogs.size == 1) {
                    firstChartDog = selectedDogs[0]
                    secondChartDog = null
                }

                if (selectedDogs.size > 1) {
                    firstChartDog = selectedDogs[0]
                    secondChartDog = selectedDogs[1]
                }

                dogs.clear()
                dogs.addAll(tempDogs)
            }

            is MainFlowEvent.SaveBrushingTimeEvent -> {
                saveBrushingTime()
            }

            is MainFlowEvent.SaveNewDog -> {
                newDogName = event.name
                getColorForNewDog()
            }

            is MainFlowEvent.PreviousPeriodClick -> {
                endDate = endDate.minusDays(7)
                startDate = startDate.minusDays(7)
                updateChardPeriod()
                Log.d("Vremena", "Start: $startDate End: $endDate")
            }

            is MainFlowEvent.NextPeriodClick -> {
                endDate = endDate.plusDays(7)
                startDate = startDate.plusDays(7)
                updateChardPeriod()
                Log.d("Vremena", "Start: $startDate End: $endDate")
            }
        }
    }

    private fun updateChardPeriod() {
        val formatedTime =
            startDate.formatDateDayMonth() + "-" + endDate.formatDateDayMonth()

        _formattedChartPeriodFLow.value = formatedTime
    }

    private fun startBrushing() {
        brushingJob?.cancel()
        brushingJob = viewModelScope.launch(Dispatchers.IO) {
            while (isActive) {
                delay(timeMillis = 1000)
                _brushingTime.value += 1
            }
        }
    }

    private fun pauseBrushing() {
        brushingJob?.cancel()
    }

    private fun stopBrushing() {
        brushingJob?.cancel()
        _brushingTime.value = 0
    }

    fun getLoggedInUser() = safeLaunch {
        call(getLoggedInUserUseCase(Unit)) {
            if (it.isLoggedIn) {
                user = it
                _userFlow.value = user
                getDogs()
            }
        }
    }

    private fun getDogs() = safeLaunch {
        call(getDogsUseCase(user.id)) {
            dogs.clear()

            if (it.isEmpty()) {
                Log.d("Dogs", "empty")
            } else {
                dogs.addAll(it)

                dogs[0] = dogs[0].copy(isSelectedForChart = true)
                firstChartDog = dogs[0]

                if (dogs.size > 1) {
                    dogs[1] = dogs[1].copy(isSelectedForChart = true)
                    secondChartDog = dogs[1]
                }

                if (insertedDogId > -1) {
                    dogs.find { it.id == insertedDogId }?.let {
                        it.isSelected = true
                    }
                }

                insertedDogId = -1
            }
        }
    }

    private fun getColorForNewDog() = safeLaunch {
        call(getColorForNewDogUseCase(user.id)) {
            newColor = it.value
            saveDog()
        }
    }

    private fun saveDog() = safeLaunch {
        var dog = DogDto(name = newDogName, ownerId = user.id, color = newColor)

        call(saveDogUseCase(SaveDogUseCase.Params(dog))) {
            insertedDogId = it
            saveColor()
        }
    }

    private fun saveColor() = safeLaunch {
        val color = ColorDto(value = newColor, ownerId = user.id, dogId = insertedDogId)
        call(saveColorUseCase(SaveColorUseCase.Params(color))) {
            getDogs()
            state = state.copy(
                shouldScrollList = true
            )
        }
    }

    private fun deleteDog() = safeLaunch {
        val deleteDogParams = deleteDog?.let { DeleteDogUseCase.Params(it) }
        deleteDogParams?.let {
            call(deleteDogUseCase(it)) {
                deleteTimesForDog()
            }
        }
    }

    private fun deleteTimesForDog() = safeLaunch {
        val deleteTimesParams = deleteDog?.let { DeleteTimeForDogUseCase.Params(it.id) }
        deleteTimesParams?.let {
            call(deleteTimeForDogUseCase(it)) {
               getDogs()
            }
        }
    }

    private fun saveBrushingTime() = safeLaunch {
        val time = brushingTime.value.toLong()
        val startTime = startBrushingDateTime
        val endTime = startTime.plusSeconds(time)
        var dogId: Long = 0

        var dog = dogs.find { it.isSelected }
        dogId = dog?.id ?: 0
        dog = dog?.copy(lastBrushingDate = endTime, lastBrushingPeriod = time)

        val userId = user.id

        val brushingTime = BrushingTimeDto(
            duration = time,
            startDateTime = startTime,
            endDateTime = endTime,
            dogId = dogId,
            ownerId = userId
        )

        val paramsDogSaving = dog?.let { UpdateDogUseCase.Params(it) }
        paramsDogSaving?.let {
            call(updateDogUseCase(it))
            {
                getDogs()
            }
        }

        val paramsBrushing = SaveBrushingTimeUseCase.Params(brushingTime)
        call(saveBrushingTimeUseCase(paramsBrushing)) {
            onEvent(MainFlowEvent.BrushingStateEvent(BrushingState.SHARING))
        }
    }


    fun formatTime(value: Int): String {
        val minutes = value / 60
        val seconds = value % 60

        return minutes.toString().padStart(2, '0') + ":" + seconds.toString().padStart(2, '0')
    }

    fun getDogNames(): List<String> {
        return dogs.map { it.name.lowercase().trim() }
    }

    fun getSelectedDog(): DogDto? {
        return dogs.find { it.isSelected }
    }
}


data class MainFlowState(
    val brushingPhase: BrushingState = BrushingState.NOT_STARTED,
    val isDogSelected: Boolean = false,
    val shouldScrollList: Boolean = false
)

sealed class MainFlowEvent() {

    data class BrushingStateEvent(val brushingState: BrushingState) : MainFlowEvent()

    data class DogClickedEvent(val dogId: Long) : MainFlowEvent()

    data class DeleteDogClickEvent(val dog: DogDto) : MainFlowEvent()

    data class DeleteDogConfirmedEvent(val temp: String) : MainFlowEvent()

    data class SaveBrushingTimeEvent(val time: String) : MainFlowEvent()

    data class SaveNewDog(val name: String) : MainFlowEvent()

    data class ChartDogClickedEvent(val dogId: Long) : MainFlowEvent()

    data class PreviousPeriodClick(val nothing: String) : MainFlowEvent()

    data class NextPeriodClick(val nothing: String) : MainFlowEvent()
}

enum class BrushingState {
    NOT_STARTED,
    IN_PROGRESS,
    CONTINUE,
    PAUSED,
    SAVING,
    SHARING
}


const val MULTI_ENTRIES_COMBINED = 2
const val GENERATOR_X_RANGE_TOP = 96
const val GENERATOR_Y_RANGE_BOTTOM = 2
const val GENERATOR_Y_RANGE_TOP = 20
const val UPDATE_FREQUENCY = 20000000000000L
