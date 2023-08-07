package com.yapp.bol.presentation.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

fun <T> Flow<T>.collectWithLifecycle(
    lifecycleOwner: LifecycleOwner,
    action: suspend CoroutineScope.(T) -> Unit,
) {
    lifecycleOwner.lifecycleScope.launchWhenStarted {
        collect {
            action(it)
        }
    }
}
