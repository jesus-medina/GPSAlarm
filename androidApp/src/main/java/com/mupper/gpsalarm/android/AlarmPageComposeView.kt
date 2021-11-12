package com.mupper.gpsalarm.android

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mupper.gpsalarm.android.theme.GPSAlarmTheme
import com.mupper.gpsalarm.presentation.viewmodel.*

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun AlarmPageComposeView(
    navController: NavController,
    isTracking: Boolean = false,
    currentLocation: LocationUI,
    alarms: List<AlarmUI>,
    retrieveAlarms: () -> Unit,
    onStartPressed: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.background(
            color = MaterialTheme.colors.background
        )
    ) {
        AlarmListComposeView(
            modifier = Modifier.fillMaxSize(),
            isTracking,
            currentLocation,
            alarms,
            onStartPressed,
        )
    }
    retrieveAlarms()
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
@Preview(showSystemUi = true)
private fun AlarmPageComposeViewPreview() {
    val navController = rememberNavController()

    AlarmPageComposeView(
        navController,
        currentLocation = LocationUI(0.0, 0.0),
        alarms = listOf(
            AlarmUI.EMPTY.copy(name = "Trabajo"),
            AlarmUI.EMPTY.copy(name = "Casa"),
        ),
        retrieveAlarms = {},
        onStartPressed = {},
    )
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun AlarmListComposeView(
    modifier: Modifier = Modifier,
    isTracking: Boolean = false,
    currentLocation: LocationUI,
    alarms: List<AlarmUI>,
    onStartPressed: () -> Unit,
) {
    ConstraintLayout(modifier = modifier) {
        val (currentLocationComposeView, alarmsLazyColumn) = createRefs()

        CurrentLocationComposeView(
            modifier = Modifier.constrainAs(currentLocationComposeView) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            isTracking,
            currentLocation,
            onStartPressed,
        )
        LazyColumn(
            modifier = modifier.constrainAs(alarmsLazyColumn) {
                top.linkTo(currentLocationComposeView.bottom)
            }
        ) {
            items(alarms) { alarm ->
                AlarmListItem(
                    alarm,
                    onAlarmActiveChange = {},
                )
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
@Preview(showBackground = true)
fun AlarmListComposeViewPreview() {
    AlarmListComposeView(
        currentLocation = LocationUI(0.0, 0.0),
        alarms = listOf(
            AlarmUI.EMPTY.copy(name = "Trabajo"),
            AlarmUI.EMPTY.copy(name = "Casa"),
        ),
        onStartPressed = {},
    )
}

@ExperimentalAnimationApi
@Composable
fun CurrentLocationComposeView(
    modifier: Modifier = Modifier,
    isTracking: Boolean = false,
    currentLocation: LocationUI,
    onStartPressed: () -> Unit,
) {
    ConstraintLayout(
        modifier = modifier
            .defaultMinSize(128.dp, 196.dp)
    ) {
        val (formattedLocationRef, startTrackingRef, formattedBeforeDestinationRef) = createRefs()
        val formattedLocation = with(currentLocation) {
            stringResource(R.string.location_format, latitude, longitude)
        }
        Text(
            formattedLocation,
            style = MaterialTheme.typography.h3,
            color = if (isTracking) MaterialTheme.colors.primary
            else MaterialTheme.colors.primary.copy(
                alpha = 0.24f
            ),
            modifier = Modifier.constrainAs(formattedLocationRef) {
                centerTo(parent)
            })

        AnimatedVisibility(
            visible = !isTracking,
            modifier = Modifier.constrainAs(startTrackingRef) {
                centerTo(parent)
            },
        ) {
            Button(
                onClick = {
                    onStartPressed()
                }) {
                Text("Start tracking")
            }
        }

        Text(
            "0.0 before work",
            color = MaterialTheme.colors.error,
            modifier = Modifier
                .constrainAs(formattedBeforeDestinationRef) {
                    top.linkTo(formattedLocationRef.bottom, margin = 16.dp)
                    start.linkTo(formattedLocationRef.start)
                    end.linkTo(formattedLocationRef.end)
                }
        )
    }
}

@ExperimentalAnimationApi
@Composable
@Preview
fun CurrentLocationComposeViewPreview() {
    val currentLocation = LocationUI(0.0, 0.0)

    LazyColumn {
        items(listOf(true, false)) {
            GPSAlarmTheme(
                darkTheme = it
            ) {
                CurrentLocationComposeView(
                    currentLocation = currentLocation,
                    onStartPressed = {},
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun AlarmListItem(
    alarmUI: AlarmUI,
    onAlarmActiveChange: ((Boolean) -> Unit),
) {
    with(alarmUI) {
        ListItem(
            modifier = Modifier
                .padding(
                    top = 8.dp,
                    bottom = 8.dp,
                    start = 4.dp,
                    end = 0.dp,
                )
                .clip(RoundedCornerShape(16.dp))
                .background(
                    color = MaterialTheme.colors.primary,
                ),
            trailing = {
                Switch(
                    checked = active,
                    onCheckedChange = onAlarmActiveChange,
                )
            },
            secondaryText = {
                with(location) {
                    Text("Latitude: $longitude, Longitude: $longitude")
                }
            },
            icon = {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(color = MaterialTheme.colors.secondaryVariant)
                ) {
                    Text(
                        "${distanceInMetersToAlert}m",
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier.align(
                            Alignment.Center
                        )
                    )
                }
            },
        ) {
            Text(name)
        }
    }
}

@ExperimentalMaterialApi
@Composable
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
fun AlarmListItemPreviewLight() {
    GPSAlarmTheme(true) {
        AlarmListItem(
            AlarmUI.EMPTY.copy(name = "Work"),
            onAlarmActiveChange = {}
        )
    }
}

@ExperimentalMaterialApi
@Composable
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
fun AlarmListItemPreviewDark() {
    GPSAlarmTheme(false) {
        AlarmListItem(
            AlarmUI.EMPTY.copy(name = "Work"),
            onAlarmActiveChange = {}
        )
    }
}