package com.comst.presentation.main.mypage

import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.comst.presentation.auth.UIAction
import com.comst.presentation.component.PDProfileImage
import com.comst.presentation.component.PDScreenHeader
import com.comst.presentation.main.group.GroupViewModel
import com.comst.presentation.ui.theme.PlanDingTheme
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun MyPageScreen(
    viewModel: MyPageViewModel = hiltViewModel()
) {

    val state = viewModel.collectAsState().value
    val context = LocalContext.current

    viewModel.collectSideEffect { sideEffect ->
        when(sideEffect) {
            is MyPageSideEffect.Toast -> Toast.makeText(
                context,
                sideEffect.message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    MyPageScreen(
        username = state.username,
        profileImageUrl = state.profileImageUrl,
        favoriteGroupsCount = state.favoriteGroupsCount,
        receivedGroupRequestsCount = state.receivedGroupRequestsCount,
        onUIAction = viewModel::onUIAction
    )
}

@Composable
private fun MyPageScreen(
    username: String = "",
    profileImageUrl: String?,
    favoriteGroupsCount: String = "0",
    receivedGroupRequestsCount: String = "0",
    onUIAction:(MyPageUIAction) -> Unit
) {
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {

                PDScreenHeader(text = "마이페이지")

                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    modifier = Modifier.padding(end = 16.dp),
                    onClick = { }
                ) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "알림",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PDProfileImage(
                    modifier = Modifier.size(100.dp),
                    profileImageUrl = profileImageUrl
                )

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = favoriteGroupsCount,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "즐겨찾는 그룹",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Gray
                    )

                }

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = receivedGroupRequestsCount,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "그룹요청 목록",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Gray
                    )

                }
            }

            Text(
                modifier = Modifier
                    .padding(top = 8.dp, start = 16.dp),
                text = username,
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "@$username",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(20.dp))

            MyPageMenu.values().forEachIndexed { index, myPageMenu ->

                Spacer(modifier = Modifier.padding(top = 8.dp))
                MyPageMenuCard(
                    menu = myPageMenu.menu,
                    menuDescription = myPageMenu.menuDescription,
                    onClickMenu = {
                        Log.d("페이지", myPageMenu.menuDescription)
                    }
                )
                if (index != MyPageMenu.values().lastIndex) {
                    Divider(modifier = Modifier.padding(top = 8.dp))
                }
            }

        }
    }
}


@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MyPageScreenPreview() {
    PlanDingTheme {
        MyPageScreen(
            username = "username",
            profileImageUrl = "null",
            favoriteGroupsCount = "dolore",
            receivedGroupRequestsCount = "urna",
            onUIAction = {}
        )
    }
}