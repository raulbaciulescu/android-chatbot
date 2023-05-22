//package com.university.androidchatbot.todo.v1
//
//import android.content.Context
//import androidx.compose.foundation.ExperimentalFoundationApi
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.graphics.ImageBitmap
//
//
//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun LongPressButton(runViewModel: RunActivityScreenViewModel, context: Context) {
//    var actionState by remember { mutableStateOf(ActivityState.PLAY) }
//    val buttonColorPlay = MaterialTheme.colorScheme.primary
//    val buttonColorStop = MaterialTheme.colors.secondaryVariant
//    val shadow = Color.Black.copy(.5f)
//    val stopIcon: ImageBitmap = ImageBitmap.imageResource(id = R.drawable.stop_icon)
//    val playIcon: ImageBitmap = ImageBitmap.imageResource(id = R.drawable.play_icon)
//    Canvas(
//        modifier = Modifier
//            .size(100.dp)
//            .combinedClickable(
//                interactionSource = MutableInteractionSource(),
//                onClick = {
//                    // Your Click Action
//                },
//                onLongClick = {
//                    // Your Long Press Action
//                },
//                indication = rememberRipple(
//                    bounded = false,
//                    radius = 20.dp,
//                    color = MaterialTheme.colors.onSurface
//                ),
//            ),
//    ) {
//        drawCircle(
//            color = shadow,
//            radius = 100f,
//            center = Offset(
//                center.x + 2f,
//                center.y + 2f
//            )
//        )
//        drawCircle(
//            color = buttonColorStop,
//            radius = 100f
//        )
//        scale(scale = 2.5f) {
//            drawImage(
//                image = stopIcon,
//                topLeft = Offset(
//                    center.x - (stopIcon.width / 2),
//                    center.y - (stopIcon.width / 2)
//                ),
//                alpha = 1f,
//            )
//        }
//    }
//}