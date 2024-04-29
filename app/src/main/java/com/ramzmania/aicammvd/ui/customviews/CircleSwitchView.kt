
package com.ramzmania.aicammvd.ui.customviews

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke

import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp


/**
 * CustomCircleSwitch: A composable function that displays a custom circle switch.
 * It allows for both regular click and long press interactions.
 *
 * @param outerCircleSize The size of the outer circle.
 * @param innerCircleSize The size of the inner circle.
 * @param outerColor The color of the outer circle.
 * @param innerColor The color of the inner circle.
 * @param onClick The callback function to be invoked on regular click.
 * @param onLongPress The callback function to be invoked on long press.
 */
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

