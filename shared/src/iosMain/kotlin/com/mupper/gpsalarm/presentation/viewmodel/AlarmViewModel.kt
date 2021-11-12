package com.mupper.gpsalarm.presentation.viewmodel

import kotlinx.coroutines.MainScope
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.container

actual abstract class AlarmViewModel : CommonViewModel<AlarmState, AlarmSideEffect> {
    actual override val scope = MainScope()

    actual override val container: Container<AlarmState, AlarmSideEffect> =
        scope.container(AlarmState(emptyList()))

    actual abstract fun retrieveAlarms()
}