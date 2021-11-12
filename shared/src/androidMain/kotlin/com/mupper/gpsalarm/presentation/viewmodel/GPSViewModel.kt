package com.mupper.gpsalarm.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container

actual abstract class GPSViewModel : ViewModel(), CommonViewModel<GPSState, GPSSideEffect> {
    actual override val scope = viewModelScope

    actual override val container: Container<GPSState, GPSSideEffect> =
        container(
            GPSState(
                isTracking = false,
                location = LocationUI(0.0, 0.0),
            )
        )

    actual abstract fun onStartPressed()
    actual abstract fun onStopPressed()
}