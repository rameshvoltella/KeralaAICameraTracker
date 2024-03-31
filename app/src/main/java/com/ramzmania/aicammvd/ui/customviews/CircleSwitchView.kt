package com.ramzmania.aicammvd.ui.customviews

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
@Composable
fun CustomCircleSwitch(outerCircleSize: Dp, innerCircleSize: Dp, outerColor:Color, innerColor:Color,onClick: () -> Unit) {
    Canvas(modifier = Modifier.size(outerCircleSize)
        .clickable { onClick.invoke() }
        .pointerInput(Unit){
            
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