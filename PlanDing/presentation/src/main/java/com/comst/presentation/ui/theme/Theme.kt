package com.comst.presentation.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val ColorScheme = lightColorScheme(
    primary = Primary300, // 밝고 주된 색상
    onPrimary = Primary400, // 주된 색상 위에 표시될 텍스트나 아이콘 색상
    primaryContainer = Primary200, // 주된 색상과 관련된 컨테이너 색상
    onPrimaryContainer = Primary300, // 주된 색상 컨테이너 위에 표시될 텍스트나 아이콘 색상
    secondary = Secondary300, // 보조 색상
    secondaryContainer = Secondary400, // 보조 색상과 관련된 컨테이너 색상
    onSecondary = Secondary400, // 보조 색상 위에 표시될 텍스트나 아이콘 색상
    onSecondaryContainer = Secondary300, // 보조 색상 컨테이너 위에 표시될 텍스트나 아이콘 색상
    surface = Background30, // 카드, 다이얼로그, 팝업 메뉴 등의 표면 색상
    onSurface = Background600, // 표면 위에 표시될 텍스트나 아이콘 색상
    background = Background20, // 앱의 배경 색상
    onBackground = Background500 // 배경 위에 표시될 텍스트나 아이콘 색상
)

@Composable
fun PlanDingTheme(
    content: @Composable () -> Unit
) {
    val darkTheme: Boolean = isSystemInDarkTheme()
    val colorScheme = ColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}