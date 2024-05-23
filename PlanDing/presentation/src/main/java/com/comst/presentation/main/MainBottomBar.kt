package com.comst.presentation.main

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.comst.presentation.ui.theme.PlanDingTheme

@Composable
fun MainBottomBar(
    navController: NavController
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute: MainRoute = navBackStackEntry
        ?.destination
        ?.route
        ?.let { currentRoute -> MainRoute.values().find { route -> route.route == currentRoute } }
        ?: MainRoute.PERSONAL_SCHEDULE

    MainBottomBar(
        currentRoute = currentRoute,
        onItemClick = { newRoute ->
            navController.navigate(route = newRoute.route) {

                /*
                // Main의 모든 스크린에서 뒤로가기 누르면 앱 종료
                popUpTo(
                    navBackStackEntry?.destination?.id ?: navController.graph.startDestinationId
                ) {
                    saveState = true
                    inclusive = true
                }
                 */

                navController.navigate(route = newRoute.route){
                    navController.graph.startDestinationRoute?.let {
                        popUpTo(it){
                            saveState = true
                        }
                    }
                    this.launchSingleTop = true
                    this.restoreState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    )
}

@Composable
private fun MainBottomBar(
    currentRoute: MainRoute,
    onItemClick: (MainRoute) -> Unit,
) {
    Column {
        Divider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MainRoute.values().forEach { route ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(
                        modifier = Modifier.size(30.dp),
                        onClick = { onItemClick(route) }
                    ) {
                        val iconPainter = route.iconPainter()
                        Icon(
                            painter = iconPainter,
                            contentDescription = route.contentDescription,
                            tint = if (currentRoute == route) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                Color.LightGray
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = route.contentDescription,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (currentRoute == route) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            Color.LightGray
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun MainBottomBarPreview() {
    PlanDingTheme {
        Surface {
            var currentRoute by remember { mutableStateOf(MainRoute.PERSONAL_SCHEDULE) }
            MainBottomBar(
                currentRoute = currentRoute,
                onItemClick = { newRoute -> currentRoute = newRoute }
            )
        }
    }
}