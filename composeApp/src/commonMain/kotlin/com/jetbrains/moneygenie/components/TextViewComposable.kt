package com.jetbrains.moneygenie.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.jetbrains.moneygenie.theme.MGTypography

/**
 * Created by Kundan on 18/08/24
 **/

enum class IconPosition {
    START, END, TOP, BOTTOM
}

@Composable
fun TextWithIcon(
    text: String,
    icon: Any,
    iconPosition: IconPosition = IconPosition.START,
    iconTint: Color = Color.Unspecified,
    textColor: Color = Color.Black,
    textStyle: TextStyle = MGTypography().bodyRegularL,
    iconSize: Int = 24,
    spacing: Int = 8,
    modifier: Modifier = Modifier
) {

    val iconContent: @Composable () -> Unit = {
        when (icon) {
            is ImageVector -> Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(iconSize.dp)
            )

            is Painter -> Icon(
                painter = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(iconSize.dp)
            )

            else -> throw IllegalArgumentException("Unsupported icon type: ${icon::class}")
        }
    }

    when (iconPosition) {
        IconPosition.START -> {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(spacing.dp),
                modifier = modifier
            ) {
                iconContent()
                Text(
                    text = text,
                    color = textColor,
                    style = textStyle
                )
            }
        }

        IconPosition.END -> {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(spacing.dp),
                modifier = modifier
            ) {
                Text(
                    text = text,
                    color = textColor,
                    style = textStyle
                )
                iconContent()
            }
        }

        IconPosition.TOP -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(spacing.dp),
                modifier = modifier
            ) {
                iconContent()
                Text(
                    text = text,
                    color = textColor,
                    style = textStyle
                )
            }
        }

        IconPosition.BOTTOM -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(spacing.dp),
                modifier = modifier
            ) {
                Text(
                    text = text,
                    color = textColor,
                    style = textStyle
                )
                iconContent()
            }
        }
    }
}

