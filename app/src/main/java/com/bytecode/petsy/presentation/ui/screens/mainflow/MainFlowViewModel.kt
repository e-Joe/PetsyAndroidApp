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
import com.bytecode.petsy.data.model.dto.brushing.PetsieChartData
import com.bytecode.petsy.data.model.dto.brushing.PetsieChartDataRequest
import com.bytecode.petsy.data.model.dto.color.ColorDto
import com.bytecode.petsy.data.model.dto.dog.DogDto
import com.bytecode.petsy.data.model.dto.language.LanguageDto
import com.bytecode.petsy.data.model.dto.user.UserDto
import com.bytecode.petsy.domain.usecase.brushingtime.DeleteTimeForDogUseCase
import com.bytecode.petsy.domain.usecase.brushingtime.DeleteTimeForUserUseCase
import com.bytecode.petsy.domain.usecase.brushingtime.GetBrushingTimesUseCase
import com.bytecode.petsy.domain.usecase.brushingtime.SaveBrushingTimeUseCase
import com.bytecode.petsy.domain.usecase.brushingtime.SaveTempBrushingTimesUseCase
import com.bytecode.petsy.domain.usecase.color.DeleteColorsForDogUseCase
import com.bytecode.petsy.domain.usecase.color.DeleteColorsForUserUseCase
import com.bytecode.petsy.domain.usecase.color.GetColorForNewDogUseCase
import com.bytecode.petsy.domain.usecase.color.SaveColorUseCase
import com.bytecode.petsy.domain.usecase.dog.DeleteDogUseCase
import com.bytecode.petsy.domain.usecase.dog.DeleteDogsUseCase
import com.bytecode.petsy.domain.usecase.dog.GetDogsUseCase
import com.bytecode.petsy.domain.usecase.dog.SaveDogUseCase
import com.bytecode.petsy.domain.usecase.dog.UpdateDogUseCase
import com.bytecode.petsy.domain.usecase.language.GetLanguageUseCase
import com.bytecode.petsy.domain.usecase.language.SaveLanguageUseCase
import com.bytecode.petsy.domain.usecase.user.DeleteUserUseCase
import com.bytecode.petsy.domain.usecase.user.GetLoggedInUserUseCase
import com.bytecode.petsy.domain.usecase.user.UpdateUserUseCase
import com.bytecode.petsy.domain.usecase.validation.ValidatePasswordDigit
import com.bytecode.petsy.domain.usecase.validation.ValidatePasswordLength
import com.bytecode.petsy.domain.usecase.validation.ValidatePasswordLowerCase
import com.bytecode.petsy.domain.usecase.validation.ValidatePasswordUpperCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
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
    private val deleteTimeForDogUseCase: DeleteTimeForDogUseCase,
    private val validatePasswordDigit: ValidatePasswordDigit,
    private val validatePasswordLowerCase: ValidatePasswordLowerCase,
    private val validatePasswordLength: ValidatePasswordLength,
    private val validatePasswordUpperCase: ValidatePasswordUpperCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val deleteDogsUseCase: DeleteDogsUseCase,
    private val deleteTimeForUserUseCase: DeleteTimeForUserUseCase,
    private val deleteColorsForDogUseCase: DeleteColorsForDogUseCase,
    private val deleteColorsForUserUseCase: DeleteColorsForUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val getBrushingTimeUseCase: GetBrushingTimesUseCase,
    private val saveTempBrushingTimesUseCase: SaveTempBrushingTimesUseCase,
    private val saveLanguageUseCase: SaveLanguageUseCase,
    private val getLanguageUseCase: GetLanguageUseCase

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

    private var petsieChartData: PetsieChartData = PetsieChartData()
    private val _petsieChartDataFlow = MutableStateFlow(petsieChartData)
    val petsieChartDataFlow: StateFlow<PetsieChartData> get() = _petsieChartDataFlow


    private lateinit var startBrushingDateTime: ZonedDateTime

    private var firstChartDog: DogDto? = null
    private var secondChartDog: DogDto? = null

    private var endDate = ZonedDateTime.now().withHour(23).withMinute(59).withSecond(59)
    private var startDate = ZonedDateTime.now().minusDays(6).withHour(0).withMinute(0).withSecond(0)

    private var formattedChartPeriod: String = ""
    private val _formattedChartPeriodFLow = MutableStateFlow(formattedChartPeriod)
    val formattedChartPeriodFlow: StateFlow<String> get() = _formattedChartPeriodFLow

    var deleteDog: DogDto = DogDto()

    val _passwordChanged = MutableStateFlow(false)
    val passwordChanged = _passwordChanged.asStateFlow()

    val _accountDeleted = MutableStateFlow(false)
    val accountDeleted = _accountDeleted.asStateFlow()

    private val _showTutorialVideo = MutableStateFlow(true)
    val showTutorialVideoFlow = _showTutorialVideo.asStateFlow()

    var dogSelected = DogDto()

    private var englishLanguage =
        LanguageDto(
            languageName = "English",
            countryCode = "GB",
            flagCode = "GB",
            isSelected = true
        )
    private val _englishLanguage = MutableStateFlow(englishLanguage)
    val englishLanguageFlow = _englishLanguage.asStateFlow()

    private var denmarkLanguage =
        LanguageDto(
            languageName = "Denmark",
            countryCode = "DK",
            flagCode = "DK",
            isSelected = false
        )
    private val _denmarkLanguage = MutableStateFlow(denmarkLanguage)
    val denmarkLanguageFlow = _denmarkLanguage.asStateFlow()

    private var romanianLanguage =
        LanguageDto(
            languageName = "Romanian",
            countryCode = "RO",
            flagCode = "RO",
            isSelected = false
        )
    private val _romanianLanguage = MutableStateFlow(romanianLanguage)
    val romanianLanguageFlow = _romanianLanguage.asStateFlow()

    private var serbianLanguage =
        LanguageDto(
            languageName = "Serbian",
            countryCode = "RS",
            flagCode = "RS",
            isSelected = false
        )
    private val _serbianLanguage = MutableStateFlow(serbianLanguage)
    val serbianLanguageFlow = _serbianLanguage.asStateFlow()

    var selectedLanguage = "GB"
    var tempLanguage = "GB"

    private val _selectedLanguage = MutableStateFlow(selectedLanguage)
    val selectedLanguageFlow = _selectedLanguage.asStateFlow()


    init {
//        saveFakeDogsTimes()
        readLanguageFunc()
        getLoggedInUser()
        updateChartPeriod()
    }

    //TODO DELETE

    private fun saveFakeDogsTimes() = safeLaunch {
        call(saveTempBrushingTimesUseCase(SaveTempBrushingTimesUseCase.Params(""))) {
            Log.d("Test", "testis")
        }
    }

    private fun readLanguageFunc() = safeLaunch {
        call(getLanguageUseCase(Unit)) {
            englishLanguage = englishLanguage.copy(isSelected = false)
            romanianLanguage = romanianLanguage.copy(isSelected = false)
            denmarkLanguage = denmarkLanguage.copy(isSelected = false)
            serbianLanguage = serbianLanguage.copy(isSelected = false)

            tempLanguage = it.countryCode
            selectedLanguage = it.countryCode
            _selectedLanguage.value = it.countryCode

            when (selectedLanguage) {
                "GB" -> {
                    englishLanguage = englishLanguage.copy(isSelected = true)
                }

                "DK" -> {
                    denmarkLanguage = denmarkLanguage.copy(isSelected = true)
                }

                "RO" -> {
                    romanianLanguage = romanianLanguage.copy(isSelected = true)
                }

                "RS" -> {
                    serbianLanguage = serbianLanguage.copy(isSelected = true)
                }
            }

            _englishLanguage.value = englishLanguage
            _romanianLanguage.value = romanianLanguage
            _denmarkLanguage.value = denmarkLanguage
            _serbianLanguage.value = serbianLanguage
        }
    }

    private fun saveLanguage(langCode: String) = safeLaunch {
        var language = LanguageDto()
        selectedLanguage = langCode
        _selectedLanguage.value = selectedLanguage

        when (tempLanguage) {
            "GB" -> {
                englishLanguage = englishLanguage.copy(isSelected = true)
                language = englishLanguage.copy()
            }

            "DK" -> {
                denmarkLanguage = denmarkLanguage.copy(isSelected = true)
                language = denmarkLanguage.copy()
            }

            "RO" -> {
                romanianLanguage = romanianLanguage.copy(isSelected = true)
                language = romanianLanguage.copy()
            }

            "RS" -> {
                serbianLanguage = serbianLanguage.copy(isSelected = true)
                language = serbianLanguage.copy()
            }
        }

        call(saveLanguageUseCase(SaveLanguageUseCase.Params(language))) {
            readLanguageFunc()
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
                        state = state.copy(brushingPhase = BrushingState.IN_PROGRESS)
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
                if (dog != null) {
                    dogSelected = dog
                }

                state = state.copy(
                    isDogSelected = dog?.isSelected ?: false
                )
            }

            is MainFlowEvent.DeleteDogClickEvent -> {
                deleteDog = event.dog
            }

            is MainFlowEvent.DeleteDogConfirmedEvent -> {
                deleteColorsForDog()
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

                getBrushingTimeForChart()
            }

            is MainFlowEvent.SaveBrushingTimeEvent -> {
                saveBrushingTime()
                state = state.copy(isDogSelected = false)
            }

            is MainFlowEvent.SaveNewDog -> {
                newDogName = event.name
                getColorForNewDog()

            }

            is MainFlowEvent.PreviousPeriodClick -> {
                endDate = endDate.minusDays(7)
                startDate = startDate.minusDays(7)
                updateChartPeriod()
                getBrushingTimeForChart()

            }

            is MainFlowEvent.NextPeriodClick -> {
                endDate = endDate.plusDays(7)
                startDate = startDate.plusDays(7)
                updateChartPeriod()
                getBrushingTimeForChart()
            }

            is MainFlowEvent.OldPasswordChanged -> {
                state = state.copy(oldPassword = event.oldPassword, oldPasswordError = null)

                if (state.isPasswordDigitValid && state.isPasswordLength && state.isPasswordLowerCaseValid && state.isPasswordUpperCaseValid && state.oldPassword.isNotEmpty())
                    state = state.copy(isPasswordChangeButtonEnabled = true)
                else
                    state = state.copy(isPasswordChangeButtonEnabled = false)
            }

            is MainFlowEvent.NewPasswordChanged -> {
                state = state.copy(newPassword = event.newPassword)
                state =
                    state.copy(isPasswordDigitValid = validatePasswordDigit.execute(state.newPassword).successful)
                state =
                    state.copy(isPasswordLength = validatePasswordLength.execute(state.newPassword).successful)
                state =
                    state.copy(isPasswordLowerCaseValid = validatePasswordLowerCase.execute(state.newPassword).successful)
                state =
                    state.copy(isPasswordUpperCaseValid = validatePasswordUpperCase.execute(state.newPassword).successful)

                if (state.isPasswordDigitValid && state.isPasswordLength && state.isPasswordLowerCaseValid && state.isPasswordUpperCaseValid && state.oldPassword.isNotEmpty())
                    state = state.copy(isPasswordChangeButtonEnabled = true)
                else
                    state = state.copy(isPasswordChangeButtonEnabled = false)
            }

            is MainFlowEvent.SavePasswordClicked -> {
                if (user.password != state.oldPassword) {
                    state = state.copy(oldPasswordError = "Please check your old password")
                    return
                } else {
                    state = state.copy(oldPasswordError = null)
                }

                updateUserPassword()
            }

            is MainFlowEvent.ResetPasswordChangeDialogEvent -> {
                _passwordChanged.value = false
            }

            is MainFlowEvent.LogoutUserEvent -> {
                logoutUser()
            }

            is MainFlowEvent.DeletePasswordChanged -> {
                state = state.copy(deletePassword = event.password)
            }

            is MainFlowEvent.DeleteUserClicked -> {
                if (user.password != state.deletePassword) {
                    state = state.copy(deletePasswordError = "Please check your password")
                    return
                } else {
                    state = state.copy(deletePasswordError = null)
                }

                deleteUser()
            }

            is MainFlowEvent.LanguageClicked -> {
                tempLanguage = event.countryCode

                englishLanguage = englishLanguage.copy(isSelected = false)
                romanianLanguage = romanianLanguage.copy(isSelected = false)
                denmarkLanguage = denmarkLanguage.copy(isSelected = false)
                serbianLanguage = serbianLanguage.copy(isSelected = false)

                when (tempLanguage) {
                    "GB" -> englishLanguage = englishLanguage.copy(isSelected = true)
                    "DK" -> denmarkLanguage = denmarkLanguage.copy(isSelected = true)
                    "RO" -> romanianLanguage = romanianLanguage.copy(isSelected = true)
                    "RS" -> serbianLanguage = serbianLanguage.copy(isSelected = true)
                }

                _englishLanguage.value = englishLanguage
                _romanianLanguage.value = romanianLanguage
                _denmarkLanguage.value = denmarkLanguage
                _serbianLanguage.value = serbianLanguage
            }

            is MainFlowEvent.SaveLanguageClicked -> {
                saveLanguage(selectedLanguage)
            }
        }
    }


    private fun updateChartPeriod() {
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
                _showTutorialVideo.value = true

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

                getBrushingTimeForChart()

                if (dogs.size == 0) {
                    _showTutorialVideo.value = true
                } else {
                    var shotTutorial = true
                    dogs.forEach {
                        if (it.lastBrushingPeriod > 0) {
                            shotTutorial = false
                        }
                    }
                    _showTutorialVideo.value = shotTutorial
                }
            }
        }
    }

    private fun getBrushingTimeForChart() = safeLaunch {
        var firstDogId: Long = -1
        var secondDogId: Long = -1

        firstChartDog?.let {
            firstDogId = it.id
        }

        secondChartDog?.let {
            secondDogId = it.id
        }
        val request = PetsieChartDataRequest(
            firstDogId,
            secondDogId,
            firstChartDog,
            secondChartDog,
            startDate.withHour(0).withMinute(0).withSecond(0),
            endDate.withHour(23).withMinute(59).withSecond(59)
        )

        call(getBrushingTimeUseCase(request)) {
            petsieChartData = it
            _petsieChartDataFlow.value = it
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
        dogSelected = dog
        state = state.copy(isDogSelected = true)

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

    private fun deleteTimesForUser() = safeLaunch {
        val deleteTimesParams = user?.let { DeleteTimeForUserUseCase.Params(it.id) }
        deleteTimesParams?.let {
            call(deleteTimeForUserUseCase(it)) {
                deleteColorsForUser()
                deleteDogsForUser()
            }
        }
    }

    private fun deleteColorsForUser() = safeLaunch {
        val deleteColorsParams = user?.let { DeleteColorsForUserUseCase.Params(it.id) }
        deleteColorsParams?.let {
            call(deleteColorsForUserUseCase(it)) {

            }
        }
    }

    private fun deleteColorsForDog() = safeLaunch {
        val deleteColorsParams = deleteDog?.let { DeleteColorsForDogUseCase.Params(it.id) }
        deleteColorsParams?.let {
            call(deleteColorsForDogUseCase(it)) {

            }
        }
    }

    private fun deleteDogsForUser() = safeLaunch {
        val deleteDogsParams = user?.let { DeleteDogsUseCase.Params(it.id) }
        deleteDogsParams?.let {
            call(deleteDogsUseCase(it)) {
                _accountDeleted.value = true
            }
        }
    }

    private fun deleteUser() = safeLaunch {
        val deleteUserParams = user?.let { DeleteUserUseCase.Params(it) }
        deleteUserParams?.let {
            call(deleteUserUseCase(it)) {
                deleteTimesForUser()
            }
        }
    }

    private fun updateUserPassword() = safeLaunch {
        user = user.copy(password = state.newPassword)
        val updateUSerParams = user?.let { UpdateUserUseCase.Params(it) }
        updateUSerParams?.let {
            call(updateUserUseCase(it)) {
                state = state.copy(oldPassword = "", newPassword = "")
                _passwordChanged.value = true
            }
        }
    }

    private fun logoutUser() = safeLaunch {
        user = user.copy(isLoggedIn = false)
        val updateUSerParams = user?.let { UpdateUserUseCase.Params(it) }
        updateUSerParams?.let {
            call(updateUserUseCase(it)) {

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
    val shouldScrollList: Boolean = false,
    val oldPassword: String = "",
    val newPassword: String = "",
    val deletePassword: String = "",
    val deletePasswordError: String? = null,
    val oldPasswordError: String? = null,
    val isPasswordDigitValid: Boolean = false,
    val isPasswordLength: Boolean = false,
    val isPasswordUpperCaseValid: Boolean = false,
    val isPasswordLowerCaseValid: Boolean = false,
    val isPasswordChangeButtonEnabled: Boolean = false
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

    data class OldPasswordChanged(val oldPassword: String) : MainFlowEvent()

    data class NewPasswordChanged(val newPassword: String) : MainFlowEvent()

    data class SavePasswordClicked(val temp: String) : MainFlowEvent()

    data class ResetPasswordChangeDialogEvent(val temp: String) : MainFlowEvent()

    data class LogoutUserEvent(val temp: String) : MainFlowEvent()

    data class DeletePasswordChanged(val password: String) : MainFlowEvent()

    data class DeleteUserClicked(val temp: String) : MainFlowEvent()

    data class LanguageClicked(val countryCode: String) : MainFlowEvent()

    data class SaveLanguageClicked(val temp: String) : MainFlowEvent()
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
