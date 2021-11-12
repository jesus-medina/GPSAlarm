package com.mupper.gpsalarm.presentation.viewmodel

import kotlinx.coroutines.MainScope
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.container

actual abstract class GPSViewModel : CommonViewModel<GPSState, GPSSideEffect> {
    actual override val scope = MainScope()

    actual override val container: Container<GPSState, GPSSideEffect> =
        scope.container(
            GPSState(
                isTracking = false,
                location = LocationUI(0.0, 0.0),
            )
        )

    actual abstract fun onStartPressed()
    actual abstract fun onStopPressed()
}