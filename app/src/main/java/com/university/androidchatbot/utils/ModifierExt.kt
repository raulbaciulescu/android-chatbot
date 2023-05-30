package com.university.androidchatbot.utils

import androidx.compose.ui.Modifier

fun Modifier.conditional(condition: Boolean, block: Modifier.() -> Modifier) =
    if (condition) {
        then(block(Modifier))
    } else {
        this
    }