package com.bytecode.petsy.presentation.ui.screens.mainflow

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.viewModelScope
import com.bytecode.framework.base.MvvmViewModel
import com.bytecode.petsy.data.model.dto.dog.DogDto
import com.bytecode.petsy.data.model.dto.user.UserDto
import com.bytecode.petsy.domain.usecase.dog.GetDogsUseCase
import com.bytecode.petsy.domain.usecase.user.GetLoggedInUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFlowViewModel @Inject constructor(
    private val getLoggedInUserUseCase: GetLoggedInUserUseCase,
    private val getDogsUseCase: GetDogsUseCase,
) : MvvmViewModel() {

    var state by mutableStateOf(MainFlowState())
    lateinit var user: UserDto

    private var job: Job? = null
    private val _times = MutableStateFlow(0)
    val times = _times.asStateFlow()

    private var dogs = mutableStateListOf(DogDto())
    private val _dogsFlow = MutableStateFlow(dogs)
    val dogsFlow: StateFlow<List<DogDto>> get() = _dogsFlow

    init {
        getLoggedInUser()
    }

    fun onEvent(event: MainFlowEvent) {

        when (event) {
            is MainFlowEvent.BrushingStateEvent -> {
                state = state.copy(brushingPhase = event.brushingState)

                when (event.brushingState) {
                    BrushingState.NOT_STARTED -> {
                        stopBrushing()
                    }

                    BrushingState.IN_PROGRESS -> {
                        startBrushing()
                    }

                    BrushingState.PAUSED -> {
                        pauseBrushing()
                    }

                    BrushingState.FINISHED -> {
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
        }
    }

    private fun startBrushing() {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            while (isActive) {
                delay(timeMillis = 1000)
                _times.value += 1
            }
        }
    }

    private fun pauseBrushing() {
        job?.cancel()
    }

    private fun stopBrushing() {
        job?.cancel()
        _times.value = 0
    }

    fun formatTime(value: Int): String {
        val minutes = value / 60
        val seconds = value % 60

        return minutes.toString().padStart(2, '0') + ":" + seconds.toString().padStart(2, '0')
    }

    private fun getLoggedInUser() = safeLaunch {
        call(getLoggedInUserUseCase(Unit)) {
            if (it.isLoggedIn) {
                user = it
                getDogs()
            }
        }
    }

    private fun getDogs() = safeLaunch {
        call(getDogsUseCase(user.id)) {
            if (it.isEmpty()) {
                Log.d("Dogs", "empty")
            } else {
                dogs.clear()
                dogs.addAll(it)
            }
        }
    }
}


data class MainFlowState(
    val brushingPhase: BrushingState = BrushingState.NOT_STARTED,
    val isDogSelected: Boolean = false
)

sealed class MainFlowEvent() {

    data class BrushingStateEvent(val brushingState: BrushingState) : MainFlowEvent()

    data class DogClickedEvent(val dogId: Long) : MainFlowEvent()
}

enum class BrushingState {
    NOT_STARTED,
    IN_PROGRESS,
    PAUSED,
    FINISHED
}