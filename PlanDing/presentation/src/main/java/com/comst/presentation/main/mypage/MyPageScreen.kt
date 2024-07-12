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
import com.comst.presentation.common.base.BaseScreen
import com.comst.presentation.component.PDProfileImage
import com.comst.presentation.component.PDScreenHeader
import com.comst.presentation.main.mypage.MyPageContract.MyPageSideEffect
import com.comst.presentation.ui.theme.PlanDingTheme


@Composable
fun MyPageScreen(
    viewModel: MyPageViewModel = hiltViewModel()
) {

    val handleEffect: (MyPageSideEffect) -> Unit =  { effect ->
        when (effect) {
            else -> {

            }
        }
    }

    BaseScreen(viewModel = viewModel, handleEffect =  handleEffect) { uiState ->
        Surface {
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PDScreenHeader(text = "마이페이지")
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { }) {
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
                        profileImageUrl = uiState.profileImageUrl
                    )

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = uiState.favoriteGroupsCount,
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
                            text = uiState.receivedGroupRequestsCount,
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
                    text = uiState.username,
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = uiState.userCode,
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
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MyPageScreenPreview() {
    PlanDingTheme {
        MyPageScreen()
    }
}