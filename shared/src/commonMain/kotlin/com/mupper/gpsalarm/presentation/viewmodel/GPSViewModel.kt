package com.mupper.gpsalarm.presentation.viewmodel

import dev.icerock.moko.geo.LatLng
import dev.icerock.moko.geo.LocationTracker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

expect abstract class GPSViewModel() : CommonViewModel<GPSState, GPSSideEffect> {
    override val scope: CoroutineScope

    override val container: Container<GPSState, GPSSideEffect>

    abstract fun onStartPressed()

    abstract fun onStopPressed()
}

class GPSViewModelImpl(
    private val locationTracker: LocationTracker
) : GPSViewModel() {

    init {
        scope.launch {
            locationTracker.getLocationsFlow()
                .distinctUntilChanged()
                .collect(::updateLocationBy)
        }
    }

    private fun updateLocationBy(latLng: LatLng) = intent {
        reduce {
            state.copy(location = LocationUI(latLng.latitude, latLng.longitude))
        }
    }

    override fun onStartPressed() = intent {
        scope.launch {
            locationTracker.startTracking()
        }
        reduce {
            state.copy(isTracking = true)
        }
    }

    override fun onStopPressed() = intent {
        locationTracker.stopTracking()
        reduce {
            state.copy(isTracking = false)
        }
    }
}

data class GPSState(
    val isTracking: Boolean,
    val location: LocationUI,
)

sealed class GPSSideEffect