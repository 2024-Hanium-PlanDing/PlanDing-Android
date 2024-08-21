package com.comst.presentation.main.mypage.groupRequestsReceived

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.comst.presentation.common.base.BaseScreen
import com.comst.presentation.component.PDScreenHeader
import com.comst.presentation.main.group.GroupCard
import com.comst.presentation.main.group.GroupContract
import com.comst.presentation.main.mypage.groupRequestsReceived.GroupRequestsReceivedContract.*
import com.comst.presentation.ui.theme.PlanDingTheme

@Composable
fun GroupRequestsReceivedScreen(
    viewModel: GroupRequestsReceivedViewModel = hiltViewModel()
) {

    val handleEffect: (GroupRequestsReceivedSideEffect) -> Unit = { effect ->

    }

    BaseScreen(viewModel = viewModel, handleEffect = handleEffect) { uiState ->
        GroupRequestsReceivedScreen(
            uiState = uiState
        )
    }
}

@Composable
private fun GroupRequestsReceivedScreen(
    uiState: GroupRequestsReceivedUIState
){
    Surface {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            PDScreenHeader(text = "그룹요청 목록")
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {

            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun GroupRequestsReceivedScreenPreview(){
    PlanDingTheme {
        GroupRequestsReceivedScreen(
            uiState = GroupRequestsReceivedUIState()
        )
    }
}