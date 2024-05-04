package com.comst.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun PDProfileImage(
    modifier: Modifier,
    profileImageUrl:String? = null,
    borderWidth: Dp = 4.dp
) {
    Box {

        Image(
            modifier = modifier
                .padding(borderWidth)
                .clip(CircleShape),
            painter = profileImageUrl?.let {
                rememberAsyncImagePainter(
                    model = profileImageUrl,
                    contentScale = ContentScale.Crop
                )
            } ?: rememberVectorPainter(image = Icons.Filled.Person),
            colorFilter = if (profileImageUrl == null) ColorFilter.tint(Color.Black) else null,
            contentDescription = "프로필 사진",
            contentScale = ContentScale.Crop,

            )
    }
}