package com.university.androidchatbot.screen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.university.androidchatbot.ui.theme.backgroundColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    onNavigationIconClick: () -> Unit
) {
//    TopAppBar(
//        title = {
//            Text(text = "hello")
//        },
//        colors = TopAppBarDefaults.topAppBarColors(),
//        navigationIcon = {
//            IconButton(onClick = onNavigationIconClick) {
//                Icon(
//                    imageVector = Icons.Default.Menu,
//                    contentDescription = "Toggle drawer"
//                )
//            }
//        }
//    )
    Row(
        modifier = Modifier.padding(horizontal = 10.dp).statusBarsPadding(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onNavigationIconClick) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Toggle drawer"
            )
        }
        Text(text = "hello")
    }
}