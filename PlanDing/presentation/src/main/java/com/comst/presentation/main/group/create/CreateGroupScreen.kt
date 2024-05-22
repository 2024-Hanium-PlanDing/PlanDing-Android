package com.comst.presentation.main.group.create

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.comst.presentation.component.PDTextFiled
import com.comst.presentation.ui.theme.PlanDingTheme
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun CreateGroupScreen(
    viewModel: CreateGroupViewModel = hiltViewModel(),
    onFinish: () -> Unit,
){
    val state = viewModel.collectAsState().value
    val context = LocalContext.current

    viewModel.collectSideEffect { sideEffect ->
        when(sideEffect){
            is CreateGroupSideEffect.Toast -> Toast.makeText(
                context,
                sideEffect.message,
                Toast.LENGTH_SHORT
            ).show()

            is CreateGroupSideEffect.Complete -> onFinish()
        }
    }

    CreateGroupScreen(
        groupName = state.groupName,
        groupDescription = state.groupDescription,
        onBackClick = onFinish,
        onUIAction = viewModel::onUIAction
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateGroupScreen(
    groupName: String,
    groupDescription: String,
    onBackClick: () -> Unit,
    onUIAction: (CreateGroupUIAction) -> Unit
) {
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
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "뒤로가기"
                            )
                        }
                    },
                    actions = {
                        TextButton(onClick = {
                            onUIAction(CreateGroupUIAction.CreateGroupRoom)
                        }) {
                            Text(text = "생성", color = Color.Black)
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
                )
            },
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                ) {
                    PDTextFiled(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        value = groupName,
                        hint = "그룹의 이름을 적어주세요.",
                        onValueChange = { newGroupName -> onUIAction(CreateGroupUIAction.GroupNameChange(newGroupName)) }
                    )

                    PDTextFiled(
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 250.dp)
                            .padding(horizontal = 16.dp),
                        value = groupDescription,
                        hint = "그룹의 설명을 적어주세요.",
                        onValueChange = { newGroupDescription -> onUIAction(CreateGroupUIAction.GroupDescriptionChange(newGroupDescription)) }
                    )
                }
            },
            bottomBar = {

            }
        )
    }
}

@Preview
@Composable
private fun CreateGroupScreenPreview() {
    PlanDingTheme {
        CreateGroupScreen(
            groupName = "Dawn Howard",
            groupDescription = "vel",
            onBackClick = {},
            onUIAction = {})
    }
}