package com.ramzmania.aicammvd.ui.customviews

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomCircleSwitch(outerCircleSize: Dp, innerCircleSize: Dp, outerColor:Color, innerColor:Color,onClick: () -> Unit, onLongPress: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    Canvas(modifier = Modifier.size(outerCircleSize)
//        .pointerInput(Unit) {
//
//        }
//        .clickable (
//            interactionSource = interactionSource,
//            indication = null,
//            onClick={
//                onClick.invoke()
//            }
//        )

//        .clickable(
//            interactionSource = interactionSource,
//            indication = null
//        ) {
//            onClick.invoke()
//        }
        .pointerInput(Unit){
            detectTapGestures(onLongPress={onLongPress.invoke()},
                onTap = {
                    onClick.invoke()
                })
//            detectPressGestures(
//                onPressStart = {
////                    onLongPress.invoke()
//
//                },
//                onLongPress = {
//                    onLongPress.invoke()
//
//                },
//                onLongPressEnd = {
////                    onLongPress.invoke()
//
//                },
//                onPressEnd = {
//                    onClick.invoke()
//                }
//            )
        }
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val strokeSize = 50f // Adjust stroke size as needed

        val outerCircleRadius = if (canvasWidth < canvasHeight) {
            canvasWidth / 2f - strokeSize / 2f // Subtract half of stroke size
        } else {
            canvasHeight / 2f - strokeSize / 2f // Subtract half of stroke size
        }

        val innerCircleRadius = outerCircleRadius * (innerCircleSize.value / outerCircleSize.value)

        val centerX = canvasWidth / 2f
        val centerY = canvasHeight / 2f

        drawCircle(
            color = outerColor,
            radius = outerCircleRadius,
            center = Offset(centerX, centerY),
            style = Stroke(width = strokeSize) // Apply stroke size here
        )

        drawCircle(
            color = innerColor,
            radius = innerCircleRadius,
            center = Offset(centerX, centerY)
        )
    }
}



@Preview
@Composable
fun PreviewCustomCircle() {
//    CustomCircle(outerCircleSize = 200.dp, innerCircleSize = 100.dp, spaceBetween = 60.dp)
    // CustomCircle(circleSize = 200.dp)
    CustomCircleSwitch(outerCircleSize = 300.dp, innerCircleSize = 250.dp,Color.Red, Color.Blue, onClick = {

    }, onLongPress = {

    })
}

inline fun Modifier.noRippleClickable(
    crossinline onClick: () -> Unit
): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

suspend fun PointerInputScope.detectPressGestures(
    onLongPress: ((Offset) -> Unit)? = null,
    onPressStart: ((Offset) -> Unit)? = null,
    onPressEnd: (() -> Unit)? = null,
    onLongPressEnd: (() -> Unit)? = null,
) = coroutineScope {

    awaitEachGesture {
        val down = awaitFirstDown()
        down.consume()

        val downTime = System.currentTimeMillis()
        onPressStart?.invoke(down.position)

        val longPressTimeout = onLongPress?.let {
            viewConfiguration.longPressTimeoutMillis
        } ?: (Long.MAX_VALUE / 2)

        var longPressInvoked = false

        do {
            val event: PointerEvent = awaitPointerEvent()
            val currentTime = System.currentTimeMillis()

            if (!longPressInvoked && currentTime - downTime >= longPressTimeout) {
                onLongPress?.invoke(event.changes.first().position)
                longPressInvoked = true
            }

            event.changes
                .forEach { pointerInputChange: PointerInputChange ->
                    pointerInputChange.consume()
                }


        } while (event.changes.any { it.pressed })

        if (longPressInvoked) {
            onLongPressEnd?.invoke()
        } else {
            onPressEnd?.invoke()
        }

    }
}



