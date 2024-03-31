import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun CustomCircle(circleSize: Dp) {
    Canvas(modifier = Modifier.size(circleSize)) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val circleRadius = if (canvasWidth < canvasHeight) {
            canvasWidth / 2f
        } else {
            canvasHeight / 2f
        }

        val centerX = canvasWidth / 2f
        val centerY = canvasHeight / 2f

        drawCircle(
            color = Color.Blue,
            radius = circleRadius,
            center = Offset(centerX, centerY)
        )
    }
}

@Composable
fun CustomCircle(outerCircleSize: Dp, innerCircleSize: Dp) {
    Canvas(modifier = Modifier.size(outerCircleSize)) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val outerCircleRadius = if (canvasWidth < canvasHeight) {
            canvasWidth / 2f
        } else {
            canvasHeight / 2f
        }

        val innerCircleRadius = outerCircleRadius * (innerCircleSize.value / outerCircleSize.value)

        val centerX = canvasWidth / 2f
        val centerY = canvasHeight / 2f

        drawCircle(
            color = Color.Blue,
            radius = outerCircleRadius,
            center = Offset(centerX, centerY),
           style = Stroke(width = 50f)

        )

        drawCircle(
            color = Color.Red,
            radius = innerCircleRadius,
            center = Offset(centerX, centerY)
        )


    }
}
@Composable
fun CustomCircle(outerCircleSize: Dp, innerCircleSize: Dp, spaceBetween: Dp) {
    Canvas(modifier = androidx.compose.ui.Modifier.size(outerCircleSize)) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val outerCircleRadius = if (canvasWidth < canvasHeight) {
            canvasWidth / 2f
        } else {
            canvasHeight / 2f
        }

        val innerCircleRadius = outerCircleRadius - spaceBetween.toPx()

        val centerX = canvasWidth / 2f
        val centerY = canvasHeight / 2f

        drawCircle(
            color = Color.Blue,
            radius = outerCircleRadius,
            center = Offset(centerX, centerY),
                    style = Stroke(width = 50f)
        )

        drawCircle(
            color = Color.Red,
            radius = innerCircleRadius,
            center = Offset(centerX, centerY)
        )
    }
}
@Composable
fun CustomCircle2(outerCircleSize: Dp, innerCircleSize: Dp,outerColor:Color,innerColor:Color) {
    Canvas(modifier = Modifier.size(outerCircleSize)) {
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
    CustomCircle2(outerCircleSize = 300.dp, innerCircleSize = 250.dp,Color.Red, Color.Blue)
}


