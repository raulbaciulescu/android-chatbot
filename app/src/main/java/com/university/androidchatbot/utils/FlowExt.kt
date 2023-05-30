package com.university.androidchatbot.utils

import kotlinx.coroutines.flow.MutableStateFlow

inline fun <T> MutableStateFlow<T>.updateValue(crossinline func: (oldValue: T) -> T) {
    val currentValue = this.value
    this.value = func(currentValue)
}