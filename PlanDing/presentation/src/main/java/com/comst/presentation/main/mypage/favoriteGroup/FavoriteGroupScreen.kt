package com.comst.presentation.main.mypage.favoriteGroup

import android.content.res.Configuration
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.comst.presentation.common.base.BaseScreen
import com.comst.presentation.component.PDScreenHeader
import com.comst.presentation.main.group.GroupCard
import com.comst.presentation.main.group.create.CreateGroupActivity
import com.comst.presentation.main.group.detail.GroupDetailActivity
import com.comst.presentation.main.mypage.favoriteGroup.FavoriteGroupContract.FavoriteGroupIntent
import com.comst.presentation.main.mypage.favoriteGroup.FavoriteGroupContract.FavoriteGroupSideEffect
import com.comst.presentation.main.mypage.favoriteGroup.FavoriteGroupContract.FavoriteGroupUIState
import com.comst.presentation.ui.theme.PlanDingTheme

@Composable
fun FavoriteGroupScreen(
    viewModel: FavoriteGroupViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val resultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        when (result.resultCode) {
            CreateGroupActivity.CREATE_GROUP -> {
                val groupResponse = CreateGroupActivity.getGroupResponseFromIntent(result.data ?: return@rememberLauncherForActivityResult)
                groupResponse?.let {

                }
            }
        }
    }

    val handelEffect: (FavoriteGroupSideEffect) -> Unit = { effect ->
        when(effect){
            is FavoriteGroupSideEffect.NavigateToGroupDetailActivity -> {
                resultLauncher.launch(
                    GroupDetailActivity.groupDetailIntent(
                        context,
                        effect.groupCode
                    )
                )
            }
        }
    }

    BaseScreen(viewModel = viewModel, handleEffect = handelEffect){ uiState ->
        FavoriteGroupScreen(
            uiState = uiState,
            setIntent = viewModel::setIntent
        )
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun FavoriteGroupScreen(
    uiState: FavoriteGroupUIState,
    setIntent: (FavoriteGroupIntent) -> Unit = {}
){
    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.isRefreshing,
        onRefresh = {
            setIntent(FavoriteGroupIntent.Refresh)
        }
    )

    Surface {
        Box(modifier = Modifier.fillMaxSize().pullRefresh(pullRefreshState)){
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                PDScreenHeader(text = "즐겨찾는 그룹")
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(
                        count = uiState.groupCardModels.size,
                        key = { index -> uiState.groupCardModels[index].groupId }
                    ) { index ->
                        uiState.groupCardModels[index].let { groupCardUIModel ->
                            GroupCard(
                                groupCardUIModel = groupCardUIModel,
                                goGroupDetail = { setIntent(FavoriteGroupIntent.GroupCardClick(groupCardUIModel.groupCode)) }
                            )

                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }

            PullRefreshIndicator(
                refreshing = uiState.isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter),
                backgroundColor = Color.LightGray,
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FavoriteGroupScreenPreview(){
    PlanDingTheme {
        FavoriteGroupScreen(

        )
    }
}