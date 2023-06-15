package com.university.androidchatbot.ui.components


import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.input.InputDialog
import com.maxkeppeler.sheets.input.models.InputHeader
import com.maxkeppeler.sheets.input.models.InputSelection
import com.maxkeppeler.sheets.input.models.InputTextField
import com.maxkeppeler.sheets.input.models.ValidationResult
import com.university.androidchatbot.R
import com.university.androidchatbot.utils.UriPathFinder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    chatTitle: String,
    onMenuClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onUpdateClick: (String) -> Unit,
    onNewPdfChatClick: (String) -> Unit,
) {
    val state = rememberUseCaseState()
    var showFilePicker by remember { mutableStateOf(false) }
    val uriPathFinder = UriPathFinder()
    val context = LocalContext.current

    val inputOptions = listOf(
        InputTextField(
            header = InputHeader(
                title = "Write a chat name:",
            ),
            validationListener = { value ->
                if ((value?.length ?: 0) >= 3) ValidationResult.Valid
                else ValidationResult.Invalid("Chat name needs to be at least 3 letters long")
            }
        )
    )

    InputDialog(
        state = state,
        selection = InputSelection(
            input = inputOptions,
            onPositiveClick = { result ->
                onUpdateClick(result.getString("0")!!)
                state.finish()
            },
        )
    )
    Row(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .statusBarsPadding(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onMenuClick) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Toggle drawer"
            )
        }
        Text(
            text = chatTitle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.weight(1f))
        if (chatTitle != "") {
            IconButton(
                modifier = Modifier.size(26.dp),
                onClick = {
                    state.show()
                }) {
                Icon(
                    painter = painterResource(R.drawable.ic_edit),
                    contentDescription = null
                )
            }
            HorizontalSpace(8.dp)
            IconButton(
                modifier = Modifier.size(26.dp),
                onClick = onDeleteClick
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_delete),
                    contentDescription = null
                )
            }
        } else {
            IconButton(
                modifier = Modifier.size(26.dp),
                onClick = {
                    showFilePicker = true
                }) {
                Icon(
                    painter = painterResource(R.drawable.ic_pdf),
                    contentDescription = null
                )
            }
        }
        FilePicker(show = showFilePicker, fileExtensions = listOf("pdf")) { path ->
            showFilePicker = false
            val myPath = uriPathFinder.handleUri(context, Uri.parse(path!!.path))
            onNewPdfChatClick(myPath!!)
            Toast.makeText(
                context,
                "Pdf file saved successfully!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}