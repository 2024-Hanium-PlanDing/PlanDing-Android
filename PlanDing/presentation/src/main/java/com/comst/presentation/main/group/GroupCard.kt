package com.comst.presentation.main.group

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.comst.presentation.ui.theme.BackgroundColor3
import com.comst.presentation.ui.theme.PlanDingTheme

@Composable
fun GroupCard(
    groupName: String,
    groupDescription: String,
    groupImageUrl: String,
    goGroupDetail: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .background(
                color = BackgroundColor3,
                shape = RoundedCornerShape(16.dp)
            ).clickable {
                goGroupDetail()
            },
        verticalAlignment = Alignment.Top
    ) {

        Image(
            painter = rememberAsyncImagePainter(model = groupImageUrl),
            contentDescription = "그룹 이미지",
            modifier = Modifier
                .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
                .width(150.dp)
                .height(100.dp)
                .clip(RoundedCornerShape(16.dp))
                .then(Modifier.background(MaterialTheme.colorScheme.surface)),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp, end = 8.dp),
                text = groupName,
                style =  MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            Text(
                modifier = Modifier.fillMaxWidth().padding(end = 8.dp),
                text = groupDescription,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                style =  MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}

@Preview
@Composable
private fun GroupCardPreview(){
    PlanDingTheme {
        GroupCard(
            groupName = "Wyatt Alston",
            groupDescription = "amet",
            groupImageUrl = "null",
            goGroupDetail = {

            }
        )
    }
}