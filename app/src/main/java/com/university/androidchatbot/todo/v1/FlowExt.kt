package com.university.androidchatbot.todo.v1

import kotlinx.coroutines.flow.MutableStateFlow

inline fun <T> MutableStateFlow<T>.updateValue(crossinline func: (oldValue: T) -> T) {
    val currentValue = this.value
    this.value = func(currentValue)
}