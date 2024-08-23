package com.comst.presentation.main.mypage.groupRequestsReceived

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.comst.domain.model.groupInvite.GroupRequestReceivedResponseModel
import com.comst.presentation.model.mypage.groupRequestsReceived.GroupRequestReceivedCardModel
import com.comst.presentation.ui.theme.BackgroundColor3
import com.comst.presentation.ui.theme.PlanDingTheme

@Composable
fun GroupRequestReceivedCard(
    groupRequestReceivedCardModel: GroupRequestReceivedCardModel,
    onAcceptClick: (GroupRequestReceivedCardModel) -> Unit,
    onDenyClick: (GroupRequestReceivedCardModel) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .background(
                color = BackgroundColor3,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable {

            }
            .height(100.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = rememberAsyncImagePainter(model = groupRequestReceivedCardModel.groupImageUrl),
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
                .fillMaxHeight()
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, end = 8.dp),
                maxLines = 1,
                text = groupRequestReceivedCardModel.groupName,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
                text = "from: ${groupRequestReceivedCardModel.invitedUserName}",
                maxLines = 1,
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray,
                textAlign = TextAlign.End
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .padding(bottom = 8.dp, end = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.clickable {
                        onDenyClick(groupRequestReceivedCardModel)
                    },
                    text = "거절",
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Red
                )

                Spacer(modifier = Modifier.width(8.dp))

                VerticalDivider(
                    color = Color.Gray,
                    modifier = Modifier
                        .height(10.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    modifier = Modifier.clickable {
                        onAcceptClick(groupRequestReceivedCardModel)
                    },
                    text = "수락",
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview
@Composable
private fun GroupRequestReceivedCardPreview(){
    PlanDingTheme {
        GroupRequestReceivedCard(
            groupRequestReceivedCardModel = GroupRequestReceivedCardModel(
                inviteCode = "latine",
                groupCode = "epicuri",
                groupName = "Wm Gilbert",
                invitedUserCode = "lorem",
                invitedUserName = "Caitlin Franklin",
                groupImageUrl = "https://www.google.com/#q=purus",
                createdAt = "eget"
            ),
            onAcceptClick = { },
            onDenyClick = { },
        )
    }
}