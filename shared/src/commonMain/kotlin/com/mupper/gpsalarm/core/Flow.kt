package com.mupper.gpsalarm.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CommonFlow<T : Any>(private val source: Flow<T>, private val scope: CoroutineScope) {
    fun collect(
        onCollect: (T) -> Unit,
        onCompletion: () -> Unit,
        onException: (Throwable) -> Unit
    ) {
        scope.launch {
            try {
                source.collect {
                    onCollect(it)
                }

                onCompletion()
            } catch (t: Throwable) {
                onException(t)
            }
        }
    }
}