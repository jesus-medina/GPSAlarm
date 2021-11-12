package com.mupper.gpsalarm.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container

actual abstract class AlarmViewModel : ViewModel(), CommonViewModel<AlarmState, AlarmSideEffect> {
    actual override val scope = viewModelScope

    actual override val container: Container<AlarmState, AlarmSideEffect> =
        container(AlarmState(emptyList()))

    actual abstract fun retrieveAlarms()
}