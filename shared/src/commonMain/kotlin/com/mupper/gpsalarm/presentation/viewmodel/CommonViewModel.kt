package com.mupper.gpsalarm.presentation.viewmodel

import kotlinx.coroutines.CoroutineScope
import org.orbitmvi.orbit.ContainerHost

interface CommonViewModel<STATE : Any, SIDE_EFFECT : Any>  : ContainerHost<STATE, SIDE_EFFECT> {
    val scope: CoroutineScope
}