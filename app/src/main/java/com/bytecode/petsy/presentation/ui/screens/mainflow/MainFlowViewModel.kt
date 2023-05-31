package com.bytecode.petsy.presentation.ui.screens.mainflow

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.bytecode.framework.base.MvvmViewModel
import com.bytecode.petsy.data.model.dto.user.UserDto
import com.bytecode.petsy.domain.usecase.dog.GetDogsUseCase
import com.bytecode.petsy.domain.usecase.user.GetLoggedInUserCase
import com.bytecode.petsy.presentation.ui.screens.loginflow.register.ValidationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFlowViewModel @Inject constructor(
    private val getLoggedInUserCase: GetLoggedInUserCase,
    private val getDogsUseCase: GetDogsUseCase,
) : MvvmViewModel() {

    var state by mutableStateOf(MainFlowState())
    lateinit var user: UserDto

    private var job: Job? = null
    private val _times = MutableStateFlow(0)
    val times = _times.asStateFlow()

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
                }

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
        call(getLoggedInUserCase(Unit)) {
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
                Log.d("Dogs", it.count().toString())
            }
        }
    }
}


data class MainFlowState(
    val brushingPhase: BrushingState = BrushingState.NOT_STARTED
)

sealed class MainFlowEvent() {

    data class BrushingStateEvent(val brushingState: BrushingState) : MainFlowEvent()
}

enum class BrushingState {
    NOT_STARTED,
    IN_PROGRESS,
    PAUSED
}