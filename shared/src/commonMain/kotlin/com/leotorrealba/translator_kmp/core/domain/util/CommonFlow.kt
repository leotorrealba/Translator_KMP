package com.leotorrealba.translator_kmp.core.domain.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

expect class CommonFlow<T>(flow: Flow<T>): Flow<T> {
    override suspend fun collect(collector: FlowCollector<T>)
}

fun <T> Flow<T>.asCommonFlow(): CommonFlow<T> = CommonFlow(this)