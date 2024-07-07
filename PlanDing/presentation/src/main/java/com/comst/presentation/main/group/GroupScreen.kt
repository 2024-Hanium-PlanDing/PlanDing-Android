package com.comst.presentation.main.group

import android.Manifest
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.comst.presentation.component.PDScreenHeader
import com.comst.presentation.main.group.create.CreateGroupActivity
import com.comst.domain.model.groupRoom.GroupRoomCardModel
import com.comst.presentation.main.group.GroupContract.GroupUIEvent
import com.comst.presentation.main.group.GroupContract.GroupUISideEffect
import com.comst.presentation.main.group.detail.GroupDetailActivity
import com.comst.presentation.ui.theme.PlanDingTheme

@Composable
fun GroupScreen(
    viewModel: GroupViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) {permissions ->
        if (permissions.all { it.value }) {
            context.startActivity(
                Intent(context, CreateGroupActivity::class.java)
            )
        } else {
            Toast.makeText(context, "필요한 권한이 부여되지 않았습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collect{ effect ->
            when(effect){
                is GroupUISideEffect.NavigateToCreateGroupActivity -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        permissionLauncher.launch(arrayOf(
                            Manifest.permission.READ_MEDIA_IMAGES,
                            Manifest.permission.READ_MEDIA_VIDEO
                        ))
                    } else {
                        permissionLauncher.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
                    }
                }

                is GroupUISideEffect.NavigateToGroupDetailActivity -> {
                    context.startActivity(
                        Intent(
                            context, GroupDetailActivity::class.java
                        ).apply {
                            putExtra("groupId", effect.id)
                        }
                    )
                }

                is GroupUISideEffect.ShowToast -> {
                    Toast.makeText(
                        context,
                        effect.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    Surface {
        Box(modifier = Modifier.fillMaxSize()){
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                PDScreenHeader(text = "나의 그룹")
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(
                        count = uiState.groupCardModels.size,
                        key = { index -> uiState.groupCardModels[index].groupId }
                    ){ index ->
                        uiState.groupCardModels[index].let {groupRoomCardModel ->
                            GroupCard(
                                groupName = groupRoomCardModel.groupName,
                                groupDescription = groupRoomCardModel.groupDescription,
                                groupImageUrl = groupRoomCardModel.groupImageUrl,
                                goGroupDetail = { viewModel.setEvent(GroupUIEvent.GroupCardClick(groupRoomCardModel.groupId)) }
                            )

                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }

            FloatingActionButton(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(60.dp),
                onClick = { viewModel.setEvent(GroupUIEvent.GroupCreateClick) },
            ) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add"
                )
            }

        }
    }
}


@Composable
private fun GroupScreen(
    groupRoomCardModels:List<GroupRoomCardModel>,
    onUIAction:(GroupUIEvent) -> Unit
){

}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun GroupScreenPreview(){
    PlanDingTheme {
        GroupScreen()
    }
}