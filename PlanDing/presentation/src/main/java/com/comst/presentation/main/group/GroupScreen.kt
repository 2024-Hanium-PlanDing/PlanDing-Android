package com.comst.presentation.main.group

import android.content.Intent
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.comst.presentation.component.PDScreenHeader
import com.comst.presentation.main.group.create.CreateGroupActivity
import com.comst.presentation.model.GroupCardModel
import com.comst.presentation.ui.theme.PlanDingTheme
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun GroupScreen(
    viewModel: GroupViewModel = hiltViewModel()
) {
    val state = viewModel.collectAsState().value
    val context = LocalContext.current

    viewModel.collectSideEffect { sideEffect ->
        when(sideEffect){
            is GroupSideEffect.Toast -> Toast.makeText(
                context,
                sideEffect.message,
                Toast.LENGTH_SHORT
            ).show()

            GroupSideEffect.NavigateToCreateGroupActivity -> {
                context.startActivity(
                    Intent(
                        context, CreateGroupActivity::class.java
                    )
                )
            }

            is GroupSideEffect.NavigateToGroupDetailActivity -> {
                context.startActivity(
                    Intent(
                        context, CreateGroupActivity::class.java
                    ).apply {
                        putExtra("", sideEffect.id)
                    }
                )
            }
        }
    }

    GroupScreen(
        groupCardModels = state.groupCardModels,
        onUIAction = viewModel::onUIAction,
    )
}


@Composable
private fun GroupScreen(
    groupCardModels:List<GroupCardModel>,
    onUIAction:(UIAction) -> Unit
){
    Surface {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            PDScreenHeader(text = "나의 그룹")
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(
                    count = groupCardModels.size,
                    key = { index -> groupCardModels[index].groupId }
                ){ index ->
                    groupCardModels[index].let { 
                        GroupCard(
                            groupName = it.groupName, 
                            groupDescription = it.groupDescription, 
                            groupImageUrl = it.groupImageUrl
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun GroupScreenPreview(){
    PlanDingTheme {
        GroupScreen(
            groupCardModels = listOf(
                GroupCardModel(
                    groupId = 6901,
                    groupName = "Gary Wallace",
                    groupImageUrl = null,
                    groupDescription = "a"
                ),
                GroupCardModel(
                    groupId = 3934,
                    groupName = "Marguerite Armstrong",
                    groupImageUrl = null,
                    groupDescription = "sociis"
                ),
                GroupCardModel(
                    groupId = 5545,
                    groupName = "Rickie Vance",
                    groupImageUrl = null,
                    groupDescription = "similique"
                )
            ),
            onUIAction = {}
        )
    }
}