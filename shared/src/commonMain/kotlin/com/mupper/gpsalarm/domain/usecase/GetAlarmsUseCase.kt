package com.mupper.gpsalarm.domain.usecase

import com.mupper.gpsalarm.presentation.viewmodel.AlarmUI

interface GetAlarmsUseCase {
    suspend operator fun invoke(): List<AlarmUI>
}

class GetAlarmsUseCaseImpl : GetAlarmsUseCase {
    override suspend fun invoke(): List<AlarmUI> = listOf(
        AlarmUI.EMPTY.copy(name = "Trabajo"),
        AlarmUI.EMPTY.copy(name = "Casa"),
        AlarmUI.EMPTY.copy(name = "Casa 1"),
        AlarmUI.EMPTY.copy(name = "Casa 2"),
        AlarmUI.EMPTY.copy(name = "Casa 3"),
        AlarmUI.EMPTY.copy(name = "Casa 4"),
        AlarmUI.EMPTY.copy(name = "Casa 5"),
        AlarmUI.EMPTY.copy(name = "Casa 6"),
        AlarmUI.EMPTY.copy(name = "Casa 7"),
        AlarmUI.EMPTY.copy(name = "Casa 8"),
        AlarmUI.EMPTY.copy(name = "Casa 9"),
        AlarmUI.EMPTY.copy(name = "Casa 10"),
    )
}