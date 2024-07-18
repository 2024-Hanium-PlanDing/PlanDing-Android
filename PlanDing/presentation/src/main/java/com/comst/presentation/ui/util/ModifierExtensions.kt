package com.comst.presentation.ui.util

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

fun Modifier.topBorder(strokeWidth: Dp, color: Color) = drawBehind {
    val strokeWidthPx = strokeWidth.toPx()
    drawLine(
        color = color,
        start = Offset(x = 0f, y = strokeWidthPx / 2),
        end = Offset(x = size.width, y = strokeWidthPx / 2),
        strokeWidth = strokeWidthPx
    )
}

fun Modifier.bottomBorder(strokeWidth: Dp, color: Color) = drawBehind {
    val strokeWidthPx = strokeWidth.toPx()
    drawLine(
        color = color,
        start = Offset(x = 0f, y = size.height - strokeWidthPx / 2),
        end = Offset(x = size.width, y = size.height - strokeWidthPx / 2),
        strokeWidth = strokeWidthPx
    )
}

fun Modifier.startBorder(strokeWidth: Dp, color: Color) = drawBehind {
    val strokeWidthPx = strokeWidth.toPx()
    drawLine(
        color = color,
        start = Offset(x = strokeWidthPx / 2, y = 0f),
        end = Offset(x = strokeWidthPx / 2, y = size.height),
        strokeWidth = strokeWidthPx
    )
}

fun Modifier.endBorder(strokeWidth: Dp, color: Color) = drawBehind {
    val strokeWidthPx = strokeWidth.toPx()
    drawLine(
        color = color,
        start = Offset(x = size.width - strokeWidthPx / 2, y = 0f),
        end = Offset(x = size.width - strokeWidthPx / 2, y = size.height),
        strokeWidth = strokeWidthPx
    )
}
