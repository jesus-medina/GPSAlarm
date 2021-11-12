package com.mupper.gpsalarm.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mupper.gpsalarm.android.Pages.PAGE_ALARM
import com.mupper.gpsalarm.android.theme.GPSAlarmTheme
import com.mupper.gpsalarm.domain.usecase.GetAlarmsUseCaseImpl
import com.mupper.gpsalarm.presentation.viewmodel.AlarmViewModelImpl
import com.mupper.gpsalarm.presentation.viewmodel.GPSViewModelImpl
import dev.icerock.moko.geo.LocationTracker
import dev.icerock.moko.permissions.PermissionsController

@ExperimentalAnimationApi
@ExperimentalMaterialApi
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val locationTracker = LocationTracker(
            permissionsController = PermissionsController(applicationContext = this)
        )
        locationTracker.bind(lifecycle, this, supportFragmentManager)

        setContent {
            GPSAlarmApp(locationTracker)
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
private fun GPSAlarmApp(
    locationTracker: LocationTracker
) {
    GPSAlarmTheme() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = PAGE_ALARM) {
            val alarmViewModel = AlarmViewModelImpl(GetAlarmsUseCaseImpl())
            val gpsViewModel = GPSViewModelImpl(locationTracker)
            composable(PAGE_ALARM) {
                val gpsState by gpsViewModel.container.stateFlow.collectAsState()
                val isTracking = gpsState.isTracking
                val currentLocation = gpsState.location
                val alarmState by alarmViewModel.container.stateFlow.collectAsState()
                val alarms = alarmState.alarms

                AlarmPageComposeView(
                    navController,
                    isTracking,
                    currentLocation,
                    alarms,
                    alarmViewModel::retrieveAlarms,
                    gpsViewModel::onStartPressed,
                )
            }
        }
    }
}

object Pages {
    const val PAGE_ALARM = "AlarmPage"
}
