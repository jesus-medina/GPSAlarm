package com.mupper.gpsalarm.presentation.viewmodel

import com.mupper.gpsalarm.domain.usecase.GetAlarmsUseCase
import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize
import kotlinx.coroutines.CoroutineScope
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

expect abstract class AlarmViewModel() : CommonViewModel<AlarmState, AlarmSideEffect> {
    override val scope: CoroutineScope

    override val container: Container<AlarmState, AlarmSideEffect>

    abstract fun retrieveAlarms()
}

class AlarmViewModelImpl(
    private val getAlarmsUseCase: GetAlarmsUseCase,
) : AlarmViewModel() {
    override fun retrieveAlarms() = intent {
        val alarms = getAlarmsUseCase()
        reduce {
            state.copy(alarms = alarms)
        }
    }
}

data class AlarmState(
    val alarms: List<AlarmUI>
)

sealed class AlarmSideEffect

data class AlarmUI(
    val name: String,
    val location: LocationUI,
    val distanceInMetersToAlert: Float,
    val active: Boolean,
) {
    companion object {
        val EMPTY = AlarmUI(
            "",
            LocationUI(
                0.0,
                0.0,
            ),
            0f,
            true,
        )
    }
}

@Parcelize
data class LocationUI(
    val latitude: Double,
    val longitude: Double,
) : Parcelable