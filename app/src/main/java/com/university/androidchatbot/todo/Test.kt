package com.university.androidchatbot.todo

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.maxkeppeker.sheets.core.models.base.IconSource
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.input.InputDialog
import com.maxkeppeler.sheets.input.models.InputHeader
import com.maxkeppeler.sheets.input.models.InputSelection
import com.maxkeppeler.sheets.input.models.InputTextField
import com.maxkeppeler.sheets.input.models.ValidationResult
import com.university.androidchatbot.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun InputSample(closeSelection: () -> Unit) {
    val inputOptions = listOf(
        InputTextField(
            header = InputHeader(
                title = "Can you tell me your first name?",
                icon = IconSource(R.drawable.ic_edit)
            ),
            validationListener = { value ->
                if ((value?.length ?: 0) >= 3) ValidationResult.Valid
                else ValidationResult.Invalid("Name needs to be at least 3 letters long")
            }
        )
    )

    InputDialog(
        state = rememberUseCaseState(visible = true, onCloseRequest = { closeSelection() }),
        selection = InputSelection(
            input = inputOptions,
            onPositiveClick = { result ->
                // Handle selection
            },
        )
    )
}