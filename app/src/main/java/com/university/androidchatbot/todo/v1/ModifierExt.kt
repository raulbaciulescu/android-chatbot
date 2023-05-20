package com.university.androidchatbot.todo.v1

import androidx.compose.ui.Modifier

fun Modifier.conditional(condition: Boolean, block: Modifier.() -> Modifier) =
    if (condition) {
        then(block(Modifier))
    } else {
        this
    }