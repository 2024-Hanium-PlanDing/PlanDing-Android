package com.comst.presentation.main.group.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.comst.domain.model.group.GroupCreateResponseModel
import com.comst.presentation.common.base.BaseScreen
import com.comst.presentation.component.PDTextField
import com.comst.presentation.main.group.create.CreateGroupContract.*
import com.comst.presentation.model.group.GroupCardUIModel
import com.comst.presentation.ui.theme.Background20
import com.comst.presentation.ui.theme.PlanDingTheme
import com.comst.presentation.ui.theme.Primary100

@Composable
fun CreateGroupScreen(
    viewModel: CreateGroupViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onFinish: (GroupCardUIModel) -> Unit
) {
    val context = LocalContext.current

    val handleEffect: (CreateGroupSideEffect) -> Unit = { effect ->
        when (effect) {
            is CreateGroupSideEffect.SuccessGroupCreation -> onFinish(effect.groupCardUIModel)
        }
    }

    BaseScreen(viewModel = viewModel, handleEffect = handleEffect) { uiState ->
        CreateGroupScreen(
            uiState = uiState,
            setIntent = viewModel::setIntent,
            onBackClick
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateGroupScreen(
    uiState: CreateGroupUIState,
    setIntent: (CreateGroupIntent) -> Unit = {},
    onBackClick: () -> Unit = {}
){
    Surface {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "새 그룹",
                            style = MaterialTheme.typography.headlineSmall
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "뒤로가기"
                            )
                        }
                    },
                    actions = {
                        TextButton(onClick = {
                            setIntent(CreateGroupIntent.CreateGroup)
                        }) {
                            Text(text = "생성", color = Color.Black)
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Primary100)
                )
            },
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    PDTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        value = uiState.groupName,
                        hint = "그룹의 이름을 적어주세요.",
                        onValueChange = { newGroupName -> setIntent(CreateGroupIntent.GroupNameChange(newGroupName)) }
                    )

                    PDTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 250.dp)
                            .padding(horizontal = 16.dp),
                        value = uiState.groupDescription,
                        hint = "그룹의 설명을 적어주세요.",
                        onValueChange = { newGroupDescription -> setIntent(CreateGroupIntent.GroupDescriptionChange(newGroupDescription)) }
                    )
                }
            }
        )
    }
}

@Preview
@Composable
private fun CreateGroupScreenPreview() {
    PlanDingTheme {
        CreateGroupScreen(
            uiState = CreateGroupUIState()
        )
    }
}