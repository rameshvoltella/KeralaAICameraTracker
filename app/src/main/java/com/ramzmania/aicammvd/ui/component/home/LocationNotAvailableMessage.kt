package com.ramzmania.aicammvd.ui.component.home

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import com.ramzmania.aicammvd.R

/**
 * LocationNotAvailableMessage: A composable function responsible for rendering a message when location
 * is not available. It provides a clickable text prompting the user to enable location settings.
 */
@Composable
fun LocationNotAvailableMessage() {
    val context= LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(0.dp, 0.dp, 0.dp, 0.dp)
            .background(colorResource(id = R.color.brown_black))
    ) {
        ClickableText(
             modifier = Modifier.align(Alignment.Center),
            text = AnnotatedString("Location not available click here to enable\n and re-Launch the app"),
            onClick = {

                val locationIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                context.startActivity(locationIntent)
            },
            style = TextStyle(
                color = colorResource(
                    id = R.color.white_perment
                ), fontSize = 16.sp,fontFamily = FontFamily(
                    typeface = ResourcesCompat.getFont(context, R.font.font_heavy)!!
                )
            )
        )

    }
}

@Preview
@Composable
fun kona()
{
    LocationNotAvailableMessage()
}