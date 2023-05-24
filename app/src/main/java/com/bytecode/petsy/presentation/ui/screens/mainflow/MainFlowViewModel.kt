package com.bytecode.petsy.presentation.ui.screens.mainflow

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.bytecode.framework.base.MvvmViewModel
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
class MainFlowViewModel @Inject constructor() : MvvmViewModel() {

    var state by mutableStateOf(MainFlowState())

    private var job: Job? = null
    private val _times = MutableStateFlow(0)
    val times = _times.asStateFlow()

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