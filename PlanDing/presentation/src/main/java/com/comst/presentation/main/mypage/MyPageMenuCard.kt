package com.comst.presentation.main.mypage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.comst.presentation.ui.theme.PlanDingTheme

@Composable
fun MyPageMenuCard(
    menu: String,
    menuDescription: String,
    onClickMenu: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable(onClick = onClickMenu),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = menu,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = Icons.Filled.KeyboardArrowRight,
            contentDescription = menuDescription,
            tint = Color.LightGray
        )

    }
}


@Preview
@Composable
private fun MyPageMenuPreview() {
    PlanDingTheme {
        MyPageMenuCard(
            menu = "delicata",
            menuDescription = "설명",
            onClickMenu = {}
        )
    }
}