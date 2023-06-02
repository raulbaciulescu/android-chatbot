package com.university.androidchatbot.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.university.androidchatbot.feature.drawer.DrawerScreen2
import com.university.androidchatbot.feature.drawer.DrawerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainBody(
    viewModel: DrawerViewModel,
    drawerState: DrawerState,
    onNewChatClick: () -> Unit,
    onNewPdfChatClick: (String) -> Unit,
    onChatClick: (Int) -> Unit,
    onLogoutSuccessful: () -> Unit,
    content: @Composable () -> Unit,
) {
    LaunchedEffect(drawerState.currentValue) {
        if(drawerState.currentValue == DrawerValue.Open) {
            viewModel.refreshChats()
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.fillMaxWidth(.8f)) {
                DrawerScreen2(
                    viewModel = viewModel,
                    onNewChatClick = onNewChatClick,
                    onNewPdfChatClick = onNewPdfChatClick,
                    onChatClick = onChatClick,
                    onLogoutSuccessful = onLogoutSuccessful
                )
            }
        },
        content = content
    )
}